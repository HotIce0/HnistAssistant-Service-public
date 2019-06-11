package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsg;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsgView;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2GoodsMsgMapper {
    @Select("SELECT * FROM t_hnist2_goods_msg")
    List<Hnist2GoodsMsg> getAll();

    @Select("SELECT * FROM t_hnist2_goods_msg WHERE id=#{id}")
    Hnist2GoodsMsg findByID(@Param("id") Integer id);

    @Select("SELECT * FROM v_hnist2_goods_msg WHERE goods_id=#{goods_id}  ORDER BY created_at ASC")
    List<Hnist2GoodsMsgView> getMsgsByGoodsID(@Param("goods_id") Integer goods_id);

    @Insert("INSERT INTO t_hnist2_goods_msg(goods_id,send_user_id,msg_id,content) values(#{goods_id},#{send_user_id},#{msg_id},#{content})")
    void insert(Hnist2GoodsMsg hnist2GoodsMsg);

    @Update("UPDATE t_hnist2_goods_msg set goods_id=#{goods_id},send_user_id=#{send_user_id},msg_id=#{msg_id},content=#{content} where id=#{id}")
    int update(Hnist2GoodsMsg hnist2GoodsMsg);

    @Delete("DELETE FROM t_hnist2_goods_msg WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);

    @Delete("DELETE FROM t_hnist2_goods_msg WHERE id = #{id} AND send_user_id=#{hnist2_id}")
    int deleteByIDAuthWithHnist2ID(@Param("id") Integer id, @Param("hnist2_id") Integer hnist2_id);
}
