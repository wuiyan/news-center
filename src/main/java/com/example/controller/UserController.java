package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

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

        return Result.ok(dbUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){

        boolean success = userService.register(user);

        if(!success){
            return Result.fail("该邮箱已注册");
        }

        return Result.ok("注册成功");
    }

}

