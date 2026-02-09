package com.example.vo;

import lombok.Data;

@Data
public class NewsVO {

    private Long id;

    private String category;

    private String title;

    private String summary;

    private String views;

    private String comments;

    private String likes;

    private String publishTime;

    private String cover;

    private Boolean isLiked;
}
