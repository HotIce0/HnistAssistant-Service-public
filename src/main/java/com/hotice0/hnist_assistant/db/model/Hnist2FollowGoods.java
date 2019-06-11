package com.hotice0.hnist_assistant.db.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author HotIce0
 * @Create 2019-05-27 09:28
 */
@Data
public class Hnist2FollowGoods implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String picture;
    private Integer is_new;
    private BigDecimal price;
    private BigDecimal purchase_price;
    private String contact_me;
    private Integer goods_type_id;
    private Integer is_free;
    private String free_require;
    private Integer owner_id;
    private Integer collection_amount;
    private Integer visit_amount;
    private Timestamp created_at;
    private Timestamp updated_at;

    private Integer fans_user_id;
    private Integer user_id;
}
