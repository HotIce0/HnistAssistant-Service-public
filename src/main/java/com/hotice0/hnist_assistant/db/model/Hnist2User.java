package com.hotice0.hnist_assistant.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author Hotice0
 */
@Data
@NoArgsConstructor
public class Hnist2User implements Serializable {
    private Integer id;
    private Integer uuid;
    private String real_name;
    private String student_id;
    private String card_id;
    private Integer sex;
    private String email;
    private Timestamp last_login_at;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Hnist2User(Integer uuid, String real_name, String student_id, String card_id, Integer sex, String email) {
        this.uuid = uuid;
        this.real_name = real_name;
        this.student_id = student_id;
        this.card_id = card_id;
        this.sex = sex;
        this.email = email;
    }
}
