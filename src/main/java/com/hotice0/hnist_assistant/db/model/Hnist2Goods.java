package com.hotice0.hnist_assistant.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author Hotice0
 */
@Data
@NoArgsConstructor
public class Hnist2Goods implements Serializable {
    public static final int STATUS_ON_SALE = 0;
    public static final int STATUS_ON_DEALING = 1;
    public static final int STATUS_SELLED = 2;

    public static final int IS_FREE_NO = 0;
    public static final int IS_FREE_YES = 1;

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


    public Hnist2Goods(String title, String description, String picture, Integer is_new, BigDecimal price, BigDecimal purchase_price, String contact_me, Integer goods_type_id, Integer is_free, String free_require, Integer owner_id, Integer collection_amount, Integer visit_amount) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.is_new = is_new;
        this.price = price;
        this.purchase_price = purchase_price;
        this.contact_me = contact_me;
        this.goods_type_id = goods_type_id;
        this.is_free = is_free;
        this.free_require = free_require;
        this.owner_id = owner_id;
        this.collection_amount = collection_amount;
        this.visit_amount = visit_amount;
    }
}
