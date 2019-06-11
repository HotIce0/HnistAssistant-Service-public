package com.hotice0.hnist_assistant.db.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author HotIce0
 * @Create 2019-05-29 17:22
 */
@Data
public class BasicConfig {
    private Integer id;
    private String app_name;
    private String config_comment;
    private String config_name;
    private String config_value;
    private Timestamp created_at;
    private Timestamp updated_at;
}
