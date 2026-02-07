package com.example.vo;

import lombok.Data;

@Data
public class AddNewsRequest {
    private String title;
    private String cover;
    private String content;
    private String category;
    private String summary;
}
