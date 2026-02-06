package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 登录
    @Select("select * from user where email=#{email} and password=#{password}")
    User login(@Param("email") String email,
               @Param("password") String password);

    // 根据邮箱查用户
    @Select("select * from user where email=#{email}")
    User findByEmail(@Param("email") String email);

    // 注册
    @Insert("insert into user(email,password,name) values(#{email},#{password},#{name})")
    int register(User user);

    // 根据ID查用户（个人信息）
    @Select("select * from user where id=#{id}")
    User selectById(@Param("id") Integer id);

    // 修改个人信息
    @Update("""
        update user
        set name = #{name},
            email = #{email},
            password = #{password}
        where id = #{id}
    """)
    int updateById(User user);
}
