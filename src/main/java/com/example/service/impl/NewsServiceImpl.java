package com.example.service.impl;

import com.example.entity.News;
import com.example.mapper.NewsCollectMapper;
import com.example.mapper.NewsLikeMapper;
import com.example.mapper.NewsMapper;
import com.example.service.NewsService;
import com.example.vo.AddNewsRequest;
import com.example.vo.NewsDetail;
import com.example.vo.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("([\\d.]+)k?");

    private int parseViewCount(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        Matcher matcher = NUMBER_PATTERN.matcher(value.toLowerCase());
        if (matcher.find()) {
            String numberStr = matcher.group(1);
            if (numberStr.contains(".")) {
                return (int) Double.parseDouble(numberStr);
            }
            return Integer.parseInt(numberStr);
        }
        return 0;
    }

    private int parseViewCount(Integer value) {
        return value == null ? 0 : value;
    }

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsLikeMapper newsLikeMapper;
    @Autowired
    private NewsCollectMapper newsCollectMapper;

    @Override
    public Map<String, Object> pageList(String category, Integer page, Integer size, Integer userId) {

        int offset = (page - 1) * size;

        List<NewsVO> list =
                newsMapper.selectPage(category, offset, size);

        for (NewsVO vo : list) {
            Integer likeCount = newsLikeMapper.count(userId, vo.getId().intValue());
            vo.setIsLiked(likeCount > 0);
        }

        Long total =
                newsMapper.count(category);

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);
        map.put("list", list);

        return map;
    }

    @Override
    public News getById(Integer id) {

        return newsMapper.selectById(id);
    }

    @Override
    @Transactional
    public void toggleLike(Integer userId, Integer newsId) {

        // 查是否点过赞
        Integer count = newsLikeMapper.count(userId, newsId);

        if(count == 0){
            // 没赞 → 点赞
            newsLikeMapper.insert(userId, newsId);
            newsMapper.addLike(newsId);

        }else{
            // 已赞 → 取消
            newsLikeMapper.delete(userId, newsId);
            newsMapper.subLike(newsId);
        }
    }

    @Override
    public NewsDetail getDetailWithLikeStatus(Integer id, Integer userId) {
        News news = newsMapper.selectById(id);
        if (news == null) {
            return null;
        }

        NewsDetail detail = new NewsDetail();
        detail.setId(news.getId());
        detail.setTitle(news.getTitle());
        detail.setContent(news.getContent());
        detail.setCategory(news.getCategory());
        detail.setCover(news.getCover());
        detail.setViewCount(parseViewCount(news.getViews()));
        detail.setLikes(parseViewCount(news.getLikes()));
        detail.setUserId(news.getUserId());
        detail.setUserName(news.getUserName());
        detail.setUserAvatar(news.getUserAvatar());
        System.out.println("原始publishTime: " + news.getPublishTime());
        if (news.getPublishTime() != null && !news.getPublishTime().isEmpty()) {
            try {
                detail.setPublishTime(LocalDateTime.parse(news.getPublishTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                try {
                    detail.setPublishTime(LocalDateTime.parse(news.getPublishTime(), DateTimeFormatter.ISO_DATE_TIME));
                } catch (Exception e2) {
                    System.out.println("时间解析失败: " + e2.getMessage());
                    detail.setPublishTime(null);
                }
            }
        }

        Integer likeCount = newsLikeMapper.count(userId, id);
        detail.setIsLiked(likeCount > 0);

        Integer collectCount = newsCollectMapper.count(userId, id);
        detail.setIsCollected(collectCount > 0);

        Long totalCollectCount = newsMapper.countCollectByNewsId(id);
        detail.setCollectCount(totalCollectCount != null ? totalCollectCount.intValue() : 0);

        return detail;
    }

    @Override
    @Transactional
    public void toggleCollect(Integer userId, Integer newsId) {
        Integer count = newsCollectMapper.count(userId, newsId);
        if (count == 0) {
            newsCollectMapper.insert(userId, newsId);
        } else {
            newsCollectMapper.delete(userId, newsId);
        }
    }

    @Override
    public boolean isCollected(Integer userId, Integer newsId) {
        return newsCollectMapper.count(userId, newsId) > 0;
    }

    @Override
    public Map<String, Object> search(String keyword, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<NewsVO> list = newsMapper.search(keyword, offset, size);
        Long total = newsMapper.searchCount(keyword);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    @Override
    public void publish(AddNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setCover(request.getCover());
        news.setContent(request.getContent());
        news.setCategory(request.getCategory());
        news.setSummary(request.getSummary());
        news.setViews("0");
        news.setLikes(0);
        news.setComments("0");
        newsMapper.insert(news);
    }

    @Override
    public Map<String, Object> getMyWorks(Integer userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<NewsVO> list = newsMapper.selectByUserId(userId, offset, size);
        Long total = newsMapper.countByUserId(userId);

        int totalViews = 0;
        int totalLikes = 0;
        for (NewsVO vo : list) {
            totalViews += parseViewCount(vo.getViews());
            totalLikes += parseViewCount(vo.getLikes());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("totalViews", totalViews);
        map.put("totalLikes", totalLikes);
        map.put("list", list);
        return map;
    }

    @Override
    public void incViewCount(Integer newsId) {
        newsMapper.addView(newsId);
    }

    @Override
    public Map<String, Object> getCollectList(Integer userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<NewsVO> list = newsMapper.selectCollectList(userId, offset, size);
        Long total = newsMapper.countCollect(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", list);
        return map;
    }

}

