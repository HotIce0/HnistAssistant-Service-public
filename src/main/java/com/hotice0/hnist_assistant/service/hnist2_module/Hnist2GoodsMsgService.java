package com.hotice0.hnist_assistant.service.hnist2_module;

import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsg;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsgView;
import com.hotice0.hnist_assistant.exception.HAException;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-22 11:39
 */
public interface Hnist2GoodsMsgService {
    void leaveGoodsMsg(Integer goods_id, Integer send_user_id, Integer msg_id, String content) throws HAException;
    Hnist2GoodsMsg getByID(Integer goods_msg_id) throws HAException;
    List<Hnist2GoodsMsgView> getMsgsByGoodsID(Integer goods_id);
    void deleteGoodsMsg(Integer msg_id, Integer hnist2_id) throws HAException;
}
