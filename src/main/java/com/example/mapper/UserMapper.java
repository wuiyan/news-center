package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 登录
    @Select("select * from user where email=#{email} and password=#{password}")
    User login(String email, String password);

    // 根据邮箱查用户（防重复注册）
    @Select("select * from user where email=#{email}")
    User findByEmail(String email);

    // 注册
    @Insert("insert into user(email,password,name) values(#{email},#{password},#{name})")
    int register(User user);
}
