package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-29 17:24
 */
@Component
@Mapper
public interface BasicConfigMapper {
    @Select("SELECT * FROM t_basic_config")
    List<BasicConfig> getAll();

    @Select("SELECT * FROM t_basic_config WHERE id = #{id} ")
    BasicConfig findByID(@Param("id") Integer id);

    @Select("SELECT * FROM t_basic_config WHERE app_name REGEXP #{app_names}")
    List<BasicConfig> getByAppName(@Param("app_names") String app_names);

    @Insert("INSERT INTO t_basic_config(config_comment,config_name,config_value,app_name) values(#{config_comment},#{config_name},#{config_value},#{app_name}})")
    void insert(BasicConfig basicConfig);

    @Update("UPDATE t_basic_config SET config_comment=#{config_comment},config_name=#{config_name},config_value=#{config_value},app_name=#{app_name} WHERE id=#{id}")
    int update(BasicConfig basicConfig);

    @Delete("DELETE FROM t_basic_config WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
