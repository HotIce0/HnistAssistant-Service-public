package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicNotification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface BasicNotificationMapper {
    @Select("SELECT * FROM t_basic_notification")
    List<BasicNotification> getAll();

    @Select("SELECT * FROM t_basic_notification WHERE id = #{id} ")
    BasicNotification findByID(@Param("id") Integer id);

    @Insert("INSERT INTO t_basic_notification(content) values(#{content})")
    void insert(BasicNotification basicNotification);

    @Update("UPDATE t_basic_notification set content=#{content} where id=#{id}")
    int update(BasicNotification basicNotification);

    @Delete("DELETE FROM t_basic_notification WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
