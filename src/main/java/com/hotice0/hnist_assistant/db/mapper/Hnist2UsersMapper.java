package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.Hnist2User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface Hnist2UsersMapper {
    @Select("SELECT * FROM t_hnist2_users")
    List<Hnist2User> getAll();

    @Select("SELECT * FROM t_hnist2_users WHERE id = #{id} ")
    Hnist2User findByID(@Param("id") Integer id);

    @Select("SELECT * FROM t_hnist2_users WHERE uuid = #{uuid} ")
    Hnist2User findByUUID(@Param("uuid") Integer uuid);

    @Select("SELECT * FROM t_hnist2_users WHERE card_id = #{card_id} ")
    Hnist2User findByCardID(@Param("card_id") String card_id);

    @Insert("INSERT INTO t_hnist2_users(uuid,real_name,student_id,card_id,sex,email) values(#{uuid},#{real_name},#{student_id},#{card_id},#{sex},#{email})")
    void insert(Hnist2User hnist2User);

    @Update("UPDATE t_hnist2_users set uuid=#{uuid},real_name=#{real_name},student_id=#{student_id},card_id=#{card_id},sex=#{sex},email=#{email} where id=#{id}")
    int update(Hnist2User hnist2User);

    @Update("UPDATE t_hnist2_users SET last_login_at=CURRENT_TIMESTAMP WHERE id=#{id}")
    void updateLastLoginAt(@Param("id") Integer id);

    @Delete("DELETE FROM t_hnist2_users WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
