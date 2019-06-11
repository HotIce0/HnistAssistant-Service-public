package com.hotice0.hnist_assistant.db.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author HotIce0
 * @Create 2019-05-26 15:29
 */
@Data
public class Hnist2GoodsDetailView implements Serializable {
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
    private String nick;
    private String avatar;
    private Timestamp last_login_at;
}
