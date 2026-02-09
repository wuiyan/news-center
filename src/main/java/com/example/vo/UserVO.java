package com.example.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String email;
    private String name;
    private String avatar;
    private Long followingCount;
    private Long followerCount;
    private Long collectCount;
}
