package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicPermission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface BasicPermissionMapper {
    @Select("SELECT * FROM t_basic_permission")
    List<BasicPermission> getAll();

    @Select("SELECT * FROM t_basic_permission WHERE id = #{id} ")
    BasicPermission findByID(@Param("id") Integer id);

    @Insert("INSERT INTO t_basic_permission(permission_no,permission_name) values(#{permission_no},#{permission_name})")
    void insert(BasicPermission basicPermission);

    @Update("UPDATE t_basic_permission set permission_no=#{permission_no},permission_name=#{permission_name} where id=#{id}")
    int update(BasicPermission basicPermission);

    @Delete("DELETE FROM t_basic_permission WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
