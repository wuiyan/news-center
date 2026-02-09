package com.example.service;

import java.util.Map;

public interface FollowService {

    void toggleFollow(Integer userId, Integer followId);

    boolean isFollowing(Integer userId, Integer followId);

    Map<String, Object> getFollowingList(Integer userId, Integer page, Integer size);

    Map<String, Object> getFollowerList(Integer userId, Integer page, Integer size);

    Long getFollowingCount(Integer userId);

    Long getFollowerCount(Integer userId);
}
