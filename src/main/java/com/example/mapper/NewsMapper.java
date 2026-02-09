package com.example.mapper;

import com.example.entity.News;
import com.example.vo.NewsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsMapper {

    /**
     * 首页分页
     */
    @Select("""
        SELECT
            id,
            category,
            title,
            summary,
            views,
            comments,
            likes,
            publish_time AS publishTime,
            cover
        FROM news
        WHERE (#{category} IS NULL OR category = #{category})
        ORDER BY id DESC
        LIMIT #{offset},#{size}
    """)
    List<NewsVO> selectPage(
            @Param("category") String category,
            @Param("offset") Integer offset,
            @Param("size") Integer size
    );


    /**
     * 总数
     */
    @Select("""
        SELECT COUNT(*)
        FROM news
        WHERE (#{category} IS NULL OR category = #{category})
    """)
    Long count(@Param("category") String category);

    @Select("SELECT * FROM news WHERE id = #{id}")
    News selectById(Integer id);

    // 点赞 +1（整数）
    @Update("""
        UPDATE news
        SET likes = IFNULL(likes, 0) + 1
        WHERE id = #{id}
""")
    void addLike(Integer id);


    // 取消点赞 -1（防止负数）
    @Update("""
      UPDATE news
      SET likes = CASE
        WHEN likes REGEXP '^[0-9.]+$' THEN GREATEST(CAST(likes AS DECIMAL(10,2)) - 1, 0)
        ELSE 0
      END
      WHERE id = #{id}
    """)
    void subLike(Integer id);

    /**
     * 搜索新闻
     */
    @Select("""
        SELECT
            id,
            category,
            title,
            summary,
            views,
            comments,
            likes,
            publish_time AS publishTime,
            cover
        FROM news
        WHERE title LIKE CONCAT('%', #{keyword}, '%')
           OR summary LIKE CONCAT('%', #{keyword}, '%')
           OR content LIKE CONCAT('%', #{keyword}, '%')
        ORDER BY id DESC
        LIMIT #{offset},#{size}
    """)
    List<NewsVO> search(@Param("keyword") String keyword,
                       @Param("offset") Integer offset,
                       @Param("size") Integer size);

    /**
     * 搜索总数
     */
    @Select("""
        SELECT COUNT(*)
        FROM news
        WHERE title LIKE CONCAT('%', #{keyword}, '%')
           OR summary LIKE CONCAT('%', #{keyword}, '%')
           OR content LIKE CONCAT('%', #{keyword}, '%')
    """)
    Long searchCount(@Param("keyword") String keyword);

    /**
     * 发布新闻
     */
    @Insert("""
        INSERT INTO news (title, cover, content, category, summary, publish_time, views, likes, comments)
        VALUES (#{title}, #{cover}, #{content}, #{category}, #{summary}, NOW(), '0', '0', '0')
    """)
    void insert(News news);

    /**
     * 获取最后插入的ID
     */
    @Select("SELECT LAST_INSERT_ID()")
    Long lastInsertId();

    /**
     * 查询用户发布的作品列表
     */
    @Select("""
        SELECT
            id,
            category,
            title,
            summary,
            views,
            comments,
            likes,
            publish_time AS publishTime,
            cover
        FROM news
        WHERE user_id = #{userId}
        ORDER BY id DESC
        LIMIT #{offset},#{size}
    """)
    List<NewsVO> selectByUserId(
            @Param("userId") Integer userId,
            @Param("offset") Integer offset,
            @Param("size") Integer size
    );

    /**
     * 查询用户发布的作品总数
     */
    @Select("""
        SELECT COUNT(*)
        FROM news
        WHERE user_id = #{userId}
    """)
    Long countByUserId(@Param("userId") Integer userId);

    /**
     * 浏览量 +1
     */
    @Update("""
        UPDATE news
        SET views = IFNULL(views, 0) + 1
        WHERE id = #{id}
    """)
    void addView(Integer id);
}


