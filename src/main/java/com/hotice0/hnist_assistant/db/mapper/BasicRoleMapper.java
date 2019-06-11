package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface BasicRoleMapper {
    @Select("SELECT * FROM t_basic_role")
    List<BasicRole> getAll();

    @Select("SELECT * FROM t_basic_role WHERE id = #{id} ")
    BasicRole findByID(@Param("id") Integer id);

    @Insert("INSERT INTO t_basic_role(role_name,role_permission) values(#{role_name},#{role_permission})")
    void insert(BasicRole basicRole);

    @Update("UPDATE t_basic_role set role_name=#{role_name},role_permission=#{role_permission} where id=#{id}")
    int update(BasicRole basicRole);

    @Delete("DELETE FROM t_basic_role WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
