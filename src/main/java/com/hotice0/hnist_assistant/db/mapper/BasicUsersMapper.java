package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface BasicUsersMapper {
    @Select("SELECT * FROM t_basic_users")
    List<BasicUser> getAll();

    @Select("SELECT * FROM t_basic_users WHERE uuid = #{uuid} ")
    BasicUser findByUUID(@Param("uuid") Integer uuid);

    @Select("SELECT * FROM t_basic_users WHERE union_id = #{union_id} ")
    BasicUser findByUNIONID(@Param("union_id") String union_id);

    @Insert("INSERT INTO t_basic_users(role_id,nick,avatar,open_id,union_id) values(#{role_id},#{nick},#{avatar},#{open_id},#{union_id})")
    void insert(BasicUser basicUser);

    @Update("UPDATE t_basic_users set role_id=#{role_id},nick=#{nick},avatar=#{avatar},open_id=#{open_id},union_id=#{union_id} where uuid=#{uuid}")
    int update(BasicUser basicUser);

    @Update("UPDATE t_basic_users SET role_id=#{role_id} WHERE uuid=#{uuid}")
    int updateRoleID(@Param("uuid") Integer uuid, @Param("role_id") Integer role_id);

    @Delete("DELETE FROM t_basic_users WHERE uuid = #{uuid}")
    int deleteByUUID(@Param("uuid") Integer uuid);
}
