package com.example.entity;

import lombok.Data;

@Data
public class News {

    private Long id;

    private String category;

    private String title;

    private String summary;

    private String content;

    private String views;

    private String comments;

    private String likes;

    private String publishTime;

    private String cover;
}
