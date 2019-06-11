package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.Hnist2Follow;
import com.hotice0.hnist_assistant.db.model.Hnist2FollowGoods;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsDetailView;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2FollowMapper {
    @Select("SELECT * FROM t_hnist2_follow")
    List<Hnist2Follow> getAll();

    @Select("SELECT * FROM t_hnist2_follow WHERE id = #{id} ")
    Hnist2Follow findByID(@Param("id") Integer id);

    @Select("SELECT * FROM t_hnist2_follow WHERE fans_user_id=#{fans_hnist2_id} ORDER BY created_at DESC")
    List<Hnist2Follow> getByFansHnist2ID(@Param("fans_hnist2_id") Integer fans_hnist2_id);

    @Select("SELECT * FROM v_hnist2_follow_goods WHERE fans_user_id=#{fans_user_id} ORDER BY created_at DESC LIMIT #{index},#{pageSize}")
    List<Hnist2GoodsDetailView> getFollowGoodsByFanHnist2ID(
            @Param("fans_user_id") Integer fans_user_id,
            @Param("index") Integer index,
            @Param("pageSize") Integer pageSize
    );

    @Insert("INSERT INTO t_hnist2_follow(fans_user_id, user_id) " +
            " SELECT #{fans_user_id}, #{user_id}" +
            " FROM DUAL" +
            " WHERE NOT EXISTS(SELECT id FROM t_hnist2_follow WHERE fans_user_id=#{fans_user_id} AND user_id=#{user_id})")
    int insert(Hnist2Follow hnist2Follow);

    @Update("UPDATE t_hnist2_follow set fans_user_id=#{fans_user_id},user_id=#{user_id} where id=#{id}")
    int update(Hnist2Follow hnist2Follow);

    @Delete("DELETE FROM t_hnist2_follow WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);

    @Delete("DELETE FROM t_hnist2_follow WHERE fans_user_id=#{fans_user_id} AND user_id=#{hnist2_id}")
    int delectByFanHnist2IDAndHnist2ID(@Param("fans_user_id") Integer fans_user_id, @Param("hnist2_id") Integer hnist2_id);
}
