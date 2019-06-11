package com.hotice0.hnist_assistant.db.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author HotIce0
 * @Create 2019-05-27 08:46
 */
@Data
public class Hnist2GoodsMsgView implements Serializable {
    private Integer id;
    private Integer goods_id;
    private Integer send_user_id;
    private Integer msg_id;
    private String content;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String nick;
    private String avatar;
    private String nick_at;
}
