package com.example.controller;

import com.example.common.Result;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/list")
    public Result list() {
        return Result.ok(categoryService.list());
    }
}
