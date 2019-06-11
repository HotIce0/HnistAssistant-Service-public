package com.hotice0.hnist_assistant.service.hnist2_module;

import com.hotice0.hnist_assistant.db.model.Hnist2Collection;
import com.hotice0.hnist_assistant.db.model.Hnist2CollectionAndGoodsInfoView;
import com.hotice0.hnist_assistant.exception.HAException;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-26 20:33
 */
public interface Hnist2CollectionService {
    void addToCollection(Integer hnist2_id, Integer goods_id) throws HAException;
    void dropFromCollection(Integer hnist2_id, Integer goods_id) throws HAException;
    List<Hnist2Collection> getCollectionByHnist2ID(Integer hnist2_id);
}
