package com.example.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NewsLikeMapper {

    @Select("""
      SELECT COUNT(*) FROM news_like
      WHERE user_id=#{userId} AND news_id=#{newsId}
    """)
    Integer count(@Param("userId") Integer userId,
                  @Param("newsId") Integer newsId);

    @Insert("""
      INSERT INTO news_like(user_id,news_id)
      VALUES(#{userId},#{newsId})
    """)
    void insert(@Param("userId") Integer userId,
                @Param("newsId") Integer newsId);

    @Delete("""
      DELETE FROM news_like
      WHERE user_id=#{userId} AND news_id=#{newsId}
    """)
    void delete(@Param("userId") Integer userId,
                @Param("newsId") Integer newsId);
}
