package com.example.service;

import com.example.entity.User;
import com.example.mapper.NewsCollectMapper;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final NewsCollectMapper newsCollectMapper;

    public UserService(UserMapper userMapper, NewsCollectMapper newsCollectMapper){
        this.userMapper = userMapper;
        this.newsCollectMapper = newsCollectMapper;
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

    // 获取个人信息
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    // 修改个人信息
    public boolean updateUser(User user) {

        // 先查原用户
        User dbUser = userMapper.selectById(user.getId());

        if(dbUser == null){
            return false;
        }

        // 如果前端没传密码 → 用原来的
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            user.setPassword(dbUser.getPassword());
        }

        // 如果没传邮箱
        if(user.getEmail() == null){
            user.setEmail(dbUser.getEmail());
        }

        // 如果没传名字
        if(user.getName() == null){
            user.setName(dbUser.getName());
        }

        return userMapper.updateById(user) > 0;
    }

    // 更新头像
    public void updateAvatar(Integer id, String avatar) {
        userMapper.updateAvatar(id, avatar);
    }

    // 获取收藏数
    public Long getCollectCount(Integer userId) {
        return newsCollectMapper.countCollect(userId);
    }

}
