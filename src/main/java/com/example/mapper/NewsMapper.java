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
            n.id,
            n.category,
            n.title,
            n.summary,
            n.views,
            n.comments,
            CAST(n.likes AS UNSIGNED) AS likes,
            n.publish_time AS publishTime,
            n.cover,
            n.user_id AS userId,
            u.name AS userName,
            u.avatar AS userAvatar,
            (SELECT COUNT(*) FROM news_collect c WHERE c.news_id = n.id) AS collectCount
        FROM news n
        LEFT JOIN user u ON n.user_id = u.id
        WHERE (#{category} IS NULL OR n.category = #{category})
        ORDER BY n.id DESC
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

    @Select("""
        SELECT
            n.id,
            n.category,
            n.title,
            n.summary,
            n.content,
            n.views,
            n.comments,
            CAST(n.likes AS UNSIGNED) AS likes,
            n.publish_time AS publishTime,
            n.cover,
            n.user_id AS userId,
            u.name AS userName,
            u.avatar AS userAvatar,
            (SELECT COUNT(*) FROM news_collect c WHERE c.news_id = n.id) AS collectCount
        FROM news n
        LEFT JOIN user u ON n.user_id = u.id
        WHERE n.id = #{id}
    """)
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
            n.id,
            n.category,
            n.title,
            n.summary,
            n.views,
            n.comments,
            CAST(n.likes AS UNSIGNED) AS likes,
            n.publish_time AS publishTime,
            n.cover,
            n.user_id AS userId,
            u.name AS userName,
            u.avatar AS userAvatar,
            (SELECT COUNT(*) FROM news_collect c WHERE c.news_id = n.id) AS collectCount
        FROM news n
        LEFT JOIN user u ON n.user_id = u.id
        WHERE n.title LIKE CONCAT('%', #{keyword}, '%')
           OR n.summary LIKE CONCAT('%', #{keyword}, '%')
           OR n.content LIKE CONCAT('%', #{keyword}, '%')
        ORDER BY n.id DESC
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
            n.id,
            n.category,
            n.title,
            n.summary,
            n.views,
            n.comments,
            CAST(n.likes AS UNSIGNED) AS likes,
            n.publish_time AS publishTime,
            n.cover,
            n.user_id AS userId,
            u.name AS userName,
            u.avatar AS userAvatar,
            (SELECT COUNT(*) FROM news_collect c WHERE c.news_id = n.id) AS collectCount
        FROM news n
        LEFT JOIN user u ON n.user_id = u.id
        WHERE n.user_id = #{userId}
        ORDER BY n.id DESC
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

    @Select("""
        SELECT
            n.id,
            n.category,
            n.title,
            n.summary,
            n.views,
            n.comments,
            CAST(n.likes AS UNSIGNED) AS likes,
            n.publish_time AS publishTime,
            n.cover,
            n.user_id AS userId,
            u.name AS userName,
            u.avatar AS userAvatar,
            (SELECT COUNT(*) FROM news_collect c2 WHERE c2.news_id = n.id) AS collectCount
        FROM news_collect c
        JOIN news n ON c.news_id = n.id
        LEFT JOIN user u ON n.user_id = u.id
        WHERE c.user_id = #{userId}
        ORDER BY c.id DESC
        LIMIT #{offset},#{size}
    """)
    List<NewsVO> selectCollectList(
            @Param("userId") Integer userId,
            @Param("offset") Integer offset,
            @Param("size") Integer size
    );

    @Select("SELECT COUNT(*) FROM news_collect WHERE user_id = #{userId}")
    Long countCollect(@Param("userId") Integer userId);

    @Select("SELECT COUNT(*) FROM news_collect WHERE news_id = #{newsId}")
    Long countCollectByNewsId(@Param("newsId") Integer newsId);
}


