package com.hotice0.hnist_assistant.service.hnist2_module.impl;

import com.hotice0.hnist_assistant.db.mapper.Hnist2CollectionMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2Collection;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2CollectionService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-26 20:34
 */
@Service
public class Hnist2CollectionServiceImpl implements Hnist2CollectionService {
    @Autowired
    Hnist2CollectionMapper hnist2CollectionMapper;
    @Autowired
    Hnist2GoodsService hnist2GoodsService;

    /**
     * 收藏商品
     * @param hnist2_id
     * @param goods_id
     * @throws HAException
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void addToCollection(Integer hnist2_id, Integer goods_id) throws HAException {
        Hnist2Collection hnist2Collection = new Hnist2Collection();
        hnist2Collection.setUser_id(hnist2_id);
        hnist2Collection.setGoods_id(goods_id);
        try{
            int affectRow = hnist2CollectionMapper.insert(hnist2Collection);
            if (affectRow < 1) {
                throw new HAException(HAError.HNIST2_COLLECTION_ITEM_EXIST);
            }
        } catch (Exception e) {
            if (e instanceof HAException)
                throw (HAException) e;
            else
                throw new HAException(HAError.HNIST2_COLLECTION_CANCEL_FAIL);
        }
        hnist2GoodsService.collection_amount_add(goods_id);
    }

    /**
     * 取消商品收藏
     * @param hnist2_id
     * @param goods_id
     * @throws HAException
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void dropFromCollection(Integer hnist2_id, Integer goods_id) throws HAException {
        int affectRow = hnist2CollectionMapper.deleteByHnist2IDAndGoodsID(hnist2_id, goods_id);
        if (affectRow < 1) {
            throw new HAException(HAError.HNIST2_COLLECTION_ITEM_NOT_EXIST);
        }
        hnist2GoodsService.collection_amount_decrease(goods_id);
    }

    /**
     * 获取我收藏的商品列表
     * @param hnist2_id
     * @return
     */
    @Override
    public List<Hnist2Collection> getCollectionByHnist2ID(Integer hnist2_id) {
        return hnist2CollectionMapper.getByHnist2ID(hnist2_id);
    }
}
