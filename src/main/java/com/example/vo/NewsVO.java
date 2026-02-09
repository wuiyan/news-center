package com.example.vo;

import lombok.Data;

@Data
public class NewsVO {

    private Long id;

    private String category;

    private String title;

    private String summary;

    private String content;

    private String views;

    private String comments;

    private Integer likes;

    private String publishTime;

    private String cover;

    private Boolean isLiked;

    private Integer collectCount;

    private Integer userId;

    private String userName;

    private String userAvatar;
}
