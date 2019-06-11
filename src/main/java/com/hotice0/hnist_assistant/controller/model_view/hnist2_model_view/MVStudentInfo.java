package com.hotice0.hnist_assistant.controller.model_view.hnist2_model_view;

import com.hotice0.hnist_assistant.db.model.Hnist2User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * getStuInfo 学生信息的视图层模型
 * @Author HotIce0
 * @Create 2019-04-18 10:47
 */
@Data
public class MVStudentInfo {
    private String real_name;// 姓名
    private String student_id;// 学号
    private String card_id; // 身份证后四位

    public static MVStudentInfo valueOfHnist2User(Hnist2User hnist2User) {
        MVStudentInfo MVStudentInfo = new MVStudentInfo();
        BeanUtils.copyProperties(hnist2User, MVStudentInfo);
        MVStudentInfo.card_id = MVStudentInfo.card_id.substring(14);
        return MVStudentInfo;
    }
}
