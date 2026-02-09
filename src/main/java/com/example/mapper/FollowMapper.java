package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FollowMapper {

    @Select("SELECT COUNT(*) FROM `follow` WHERE user_id=#{userId} AND follow_id=#{followId}")
    Integer count(@Param("userId") Integer userId, @Param("followId") Integer followId);

    @Insert("INSERT INTO `follow`(user_id, follow_id) VALUES(#{userId}, #{followId})")
    void insert(@Param("userId") Integer userId, @Param("followId") Integer followId);

    @Delete("DELETE FROM `follow` WHERE user_id=#{userId} AND follow_id=#{followId}")
    void delete(@Param("userId") Integer userId, @Param("followId") Integer followId);

    @Select("""
        SELECT u.id, u.name, u.avatar
        FROM `follow` f
        JOIN user u ON f.follow_id = u.id
        WHERE f.user_id = #{userId}
        ORDER BY f.id DESC
        LIMIT #{offset}, #{size}
    """)
    List<User> selectFollowingList(@Param("userId") Integer userId,
                                   @Param("offset") Integer offset,
                                   @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM `follow` WHERE user_id = #{userId}")
    Long countFollowing(@Param("userId") Integer userId);

    @Select("""
        SELECT u.id, u.name, u.avatar
        FROM `follow` f
        JOIN user u ON f.user_id = u.id
        WHERE f.follow_id = #{userId}
        ORDER BY f.id DESC
        LIMIT #{offset}, #{size}
    """)
    List<User> selectFollowerList(@Param("userId") Integer userId,
                                   @Param("offset") Integer offset,
                                   @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM `follow` WHERE follow_id = #{userId}")
    Long countFollower(@Param("userId") Integer userId);
}
