package com.example.controller;

import com.example.common.Result;
import com.example.service.NewsService;
import com.example.vo.AddNewsRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 首页列表
     */
    @GetMapping("/list")
    public Result list(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return Result.ok(
                newsService.pageList(category, page, size)
        );
    }
    // ⭐ 详情接口
    // NewsController.java
    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return Result.ok(newsService.getDetailWithLikeStatus(id, userId));
    }
    //点赞
    @PostMapping("/like/{newsId}")
    public Result like(@PathVariable Integer newsId,
                       HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("userId");

        newsService.toggleLike(userId, newsId);

        return Result.ok();
    }

//    @GetMapping("/liked/{newsId}")
//    public Result liked(@PathVariable Integer newsId,
//                        HttpServletRequest request){
//
//        Integer userId = (Integer)request.getAttribute("userId");
//
//        return Result.ok(
//                newsService.isLiked(userId,newsId)
//        );
//    }

    //收藏
    @PostMapping("/collect/{newsId}")
    public Result collect(@PathVariable Integer newsId,
                          HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        newsService.toggleCollect(userId, newsId);
        return Result.ok();
    }

    /**
     * 搜索新闻
     */
    @GetMapping("/search")
    public Result search(@RequestParam String keyword,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.ok(newsService.search(keyword, page, size));
    }

    /**
     * 发布新闻
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody AddNewsRequest request) {
        newsService.publish(request);
        return Result.ok();
    }

}


