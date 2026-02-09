package com.example.service;

import com.example.entity.News;
import com.example.vo.AddNewsRequest;
import com.example.vo.NewsDetail;

import java.util.Map;

public interface NewsService {

    Map<String, Object> pageList(String category, Integer page, Integer size, Integer userId);

    News getById(Integer id);   // ⭐ 新增

    void toggleLike(Integer userId, Integer newsId);

    NewsDetail getDetailWithLikeStatus(Integer id, Integer userId);

    void toggleCollect(Integer userId, Integer newsId);

    boolean isCollected(Integer userId, Integer newsId);

    Map<String, Object> search(String keyword, Integer page, Integer size);

    void publish(AddNewsRequest request);

    Map<String, Object> getMyWorks(Integer userId, Integer page, Integer size);

    void incViewCount(Integer newsId);
}

