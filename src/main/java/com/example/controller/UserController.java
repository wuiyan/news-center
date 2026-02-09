package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.FollowService;
import com.example.service.UserService;
import com.example.vo.UserVO;
import org.springframework.web.bind.annotation.*;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    public UserController(UserService userService, FollowService followService){
        this.userService = userService;
        this.followService = followService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){

        User dbUser = userService.login(
                user.getEmail(),
                user.getPassword()
        );

        if(dbUser == null){
            return Result.fail("账号或密码错误");
        }

        // 生成token
        String token = JwtUtil.createToken(dbUser.getId());

        return Result.ok(token);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){

        boolean success = userService.register(user);

        if(!success){
            return Result.fail("该邮箱已注册");
        }

        return Result.ok("注册成功");
    }

    // 获取个人信息
    @GetMapping("/me")
    public Result me(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");

        User user = userService.getById(userId);
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setEmail(user.getEmail());
        userVO.setName(user.getName());
        userVO.setAvatar(user.getAvatar());
        userVO.setFollowingCount(followService.getFollowingCount(userId));
        userVO.setFollowerCount(followService.getFollowerCount(userId));
        userVO.setCollectCount(userService.getCollectCount(userId));

        return Result.ok(userVO);
    }



    // 修改个人信息
    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){

        boolean success = userService.updateUser(user);

        if(!success){
            return Result.fail("修改失败");
        }

        return Result.ok("修改成功");
    }

    // 修改头像
    @PutMapping("/avatar")
    public Result updateAvatar(@RequestParam String avatar, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        userService.updateAvatar(userId, avatar);
        return Result.ok("头像修改成功");
    }

    // 关注/取消关注用户
    @PostMapping("/follow/{userId}")
    public Result follow(@PathVariable Integer userId, HttpServletRequest request){
        Integer myId = (Integer) request.getAttribute("userId");
        followService.toggleFollow(myId, userId);
        return Result.ok();
    }

    // 我的关注列表
    @GetMapping("/following")
    public Result getFollowingList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return Result.ok(followService.getFollowingList(userId, page, size));
    }

    // 我的粉丝列表
    @GetMapping("/followers")
    public Result getFollowerList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return Result.ok(followService.getFollowerList(userId, page, size));
    }

}

