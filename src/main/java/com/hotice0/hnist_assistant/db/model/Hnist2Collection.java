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
public class Hnist2Collection  implements Serializable {
    private Integer id;
    private Integer user_id;
    private Integer goods_id;
    private Timestamp created_at;
    private Timestamp updated_at;
}
