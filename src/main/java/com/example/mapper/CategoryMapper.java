package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT DISTINCT category AS name FROM news")
    List<Category> selectCategories();   // ← 名字必须一样
}
