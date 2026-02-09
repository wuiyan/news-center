package com.example.vo;

import java.time.LocalDateTime;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsDetail {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String cover;
    private Integer viewCount;
    private Integer likes;
    private LocalDateTime publishTime;

    private Integer userId;
    private String userName;
    private String userAvatar;

    private Boolean isLiked;
    private Boolean isCollected;
    private Integer collectCount;
}