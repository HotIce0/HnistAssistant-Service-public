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
public class Hnist2GoodsMsg implements Serializable {
    private Integer id;
    private Integer goods_id;
    private Integer send_user_id;
    private Integer msg_id;
    private String content;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Hnist2GoodsMsg(Integer goods_id, Integer send_user_id, Integer msg_id, String content) {
        this.goods_id = goods_id;
        this.send_user_id = send_user_id;
        this.msg_id = msg_id;
        this.content = content;
    }
}
