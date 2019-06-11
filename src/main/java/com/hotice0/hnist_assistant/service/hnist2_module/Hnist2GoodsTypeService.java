package com.hotice0.hnist_assistant.service.hnist2_module;

import com.hotice0.hnist_assistant.db.model.Hnist2GoodsType;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-19 11:00
 */
public interface Hnist2GoodsTypeService {
    List<Hnist2GoodsType> getAll();

    void add(String name);

    void del(Integer id);

    void update(Integer id, String name);
}
