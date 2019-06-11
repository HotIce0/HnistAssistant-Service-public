package com.hotice0.hnist_assistant.service.hnist2_module;

import com.hotice0.hnist_assistant.db.model.Hnist2FollowGoods;
import com.hotice0.hnist_assistant.db.model.Hnist2Goods;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsDetailView;
import com.hotice0.hnist_assistant.exception.HAException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-19 10:58
 */
public interface Hnist2GoodsService {
    List<Hnist2GoodsDetailView> getByGoodsType(Integer goods_type_id, Integer sort_type, Integer index, Integer pageSize);

    List<Hnist2Goods> getGoodsByHnist2ID(Integer hnist2_id, Integer index, Integer pageSize);

    Hnist2Goods getByID(Integer id) throws HAException;

    void add(Hnist2Goods hnist2Goods) throws HAException;

    void del(Hnist2Goods hnist2Goods) throws HAException;

    void update(Hnist2Goods hnist2Goods, String originPicture) throws HAException;

    void visit_amount_add(Integer goods_id);

    void collection_amount_add(Integer goods_id) throws HAException;

    void collection_amount_decrease(Integer goods_id) throws HAException;

    Hnist2GoodsDetailView getGoodsDetailByID(Integer goods_id) throws HAException;

    List<Hnist2GoodsDetailView> getGoodsDetailByIDs(List<Integer> goods_ids);

    List<Hnist2GoodsDetailView> getFollowGoods(Integer fans_user_id, Integer index, Integer pageSize);

    List<Hnist2GoodsDetailView> searchByKeyword(String keyword, Integer index, Integer pageSize);
}
