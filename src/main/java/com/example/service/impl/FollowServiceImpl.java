package com.example.service.impl;

import com.example.entity.User;
import com.example.mapper.FollowMapper;
import com.example.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public void toggleFollow(Integer userId, Integer followId) {
        Integer count = followMapper.count(userId, followId);
        if (count == 0) {
            followMapper.insert(userId, followId);
        } else {
            followMapper.delete(userId, followId);
        }
    }

    @Override
    public boolean isFollowing(Integer userId, Integer followId) {
        return followMapper.count(userId, followId) > 0;
    }

    @Override
    public Map<String, Object> getFollowingList(Integer userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<User> list = followMapper.selectFollowingList(userId, offset, size);
        Long total = followMapper.countFollowing(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    @Override
    public Map<String, Object> getFollowerList(Integer userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<User> list = followMapper.selectFollowerList(userId, offset, size);
        Long total = followMapper.countFollower(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    @Override
    public Long getFollowingCount(Integer userId) {
        return followMapper.countFollowing(userId);
    }

    @Override
    public Long getFollowerCount(Integer userId) {
        return followMapper.countFollower(userId);
    }
}
