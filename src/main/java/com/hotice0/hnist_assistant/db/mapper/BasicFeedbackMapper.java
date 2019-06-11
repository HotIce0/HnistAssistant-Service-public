package com.hotice0.hnist_assistant.db.mapper;

import com.hotice0.hnist_assistant.db.model.BasicFeedback;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Hotice0
 */
@Component
@Mapper
public interface BasicFeedbackMapper {
    @Select("SELECT * FROM t_basic_feedback")
    List<BasicFeedback> getAll();

    @Select("SELECT * FROM t_basic_feedback WHERE id = #{id} ")
    BasicFeedback findByID(@Param("id") Integer id);

    @Insert("INSERT INTO t_basic_feedback(uuid,feedback_content) values(#{uuid},#{feedback_content})")
    void insert(BasicFeedback basicFeedback);

    @Update("UPDATE t_basic_feedback set uuid=#{uuid},feedback_content=#{feedback_content} where id=#{id}")
    int update(BasicFeedback basicFeedback);

    @Delete("DELETE FROM t_basic_feedback WHERE id = #{id}")
    int deleteByID(@Param("id") Integer id);
}
