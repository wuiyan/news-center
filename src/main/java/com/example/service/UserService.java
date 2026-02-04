package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    // 登录
    public User login(String email,String password){
        return userMapper.login(email,password);
    }

    // 注册
    public boolean register(User user){

        // 查是否存在
        User exist = userMapper.findByEmail(user.getEmail());

        if(exist != null){
            return false; // 已注册
        }

        userMapper.register(user);
        return true;
    }
}
