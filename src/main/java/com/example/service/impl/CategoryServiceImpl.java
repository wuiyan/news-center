package com.example.service.impl;

import com.example.service.CategoryService;
import com.example.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryVO> list() {

        List<CategoryVO> list = new ArrayList<>();

        list.add(build("all", "全部", "📱"));
        list.add(build("tech", "科技", "💻"));
        list.add(build("finance", "财经", "💰"));
        list.add(build("entertainment", "娱乐", "🎬"));
        list.add(build("sports", "体育", "⚽"));
        list.add(build("life", "生活", "🌟"));

        return list;
    }

    private CategoryVO build(String id, String name, String icon) {
        CategoryVO vo = new CategoryVO();
        vo.setId(id);
        vo.setName(name);
        vo.setIcon(icon);
        return vo;
    }
}
