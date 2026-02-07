package com.example.vo;

import java.time.LocalDateTime;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsDetail {
    // 原有新闻字段
    private Long id;
    private String title;
    private String content;
    private String category;
    private String cover;
    private Integer viewCount;
    private Integer likes;
    private LocalDateTime createTime;


    // ⭐ 新增：当前用户是否已点赞
    private Boolean isLiked;

    // ⭐ 新增：当前用户是否已收藏
    private Boolean isCollected;
}