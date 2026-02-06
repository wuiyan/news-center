package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
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

        Object uid = request.getAttribute("userId");

        System.out.println("Controller userId = " + uid);

        return Result.ok(userService.getById((Integer) uid));
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

}

