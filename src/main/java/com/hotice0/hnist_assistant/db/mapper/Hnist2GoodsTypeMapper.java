package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.Hnist2GoodsType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2GoodsTypeMapper {
    @Select("SELECT * FROM t_hnist2_goods_type")
    List<Hnist2GoodsType> getAll();

    @Select("SELECT * FROM t_hnist2_goods_type WHERE id = #{id} ")
    Hnist2GoodsType findByID(@Param("id") Integer id);

    @Insert("INSERT INTO t_hnist2_goods_type(name) values(#{name})")
    void insert(Hnist2GoodsType hnist2GoodsType);

    @Update("UPDATE t_hnist2_goods_type set name=#{name} where id=#{id}")
    int update(Hnist2GoodsType hnist2GoodsType);

    @Delete("DELETE FROM t_hnist2_goods_type WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
