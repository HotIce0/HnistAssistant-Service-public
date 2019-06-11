package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.Hnist2Collection;
import com.hotice0.hnist_assistant.db.model.Hnist2CollectionAndGoodsInfoView;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2CollectionMapper {
    @Select("SELECT * FROM t_hnist2_collection")
    List<Hnist2Collection> getAll();

    @Select("SELECT * FROM t_hnist2_collection WHERE id = #{id} ")
    Hnist2Collection findByID(@Param("id") Integer id);

    @Select("SELECT * FROM t_hnist2_collection WHERE user_id=#{hnist2_id} ORDER BY created_at DESC")
    List<Hnist2Collection> getByHnist2ID(@Param("hnist2_id") Integer hnist2_id);

//    @Select("SELECT * FROM v_hnist2_goods_collection WHERE collect_user_id=#{hnist2_id} ORDER BY collection_created_at DESC")
//    List<Hnist2CollectionAndGoodsInfoView> getByHnist2ID(@Param("hnist2_id") Integer hnist2_id);

    @Insert("INSERT INTO t_hnist2_collection(user_id, goods_id) " +
            " SELECT #{user_id}, #{goods_id}" +
            " FROM DUAL" +
            " WHERE NOT EXISTS(SELECT id FROM t_hnist2_collection WHERE user_id=#{user_id} AND goods_id=#{goods_id})")
    int insert(Hnist2Collection hnist2Collection);

    @Update("UPDATE t_hnist2_collection set user_id=#{user_id},goods_id=#{goods_id} where id=#{id}")
    int update(Hnist2Collection hnist2Collection);

    @Delete("DELETE FROM t_hnist2_collection WHERE user_id=#{hnist2_id} AND goods_id=#{goods_id}")
    int deleteByHnist2IDAndGoodsID(@Param("hnist2_id") Integer hnist2_id, @Param("goods_id") Integer goods_id);

    @Delete("DELETE FROM t_hnist2_collection WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
