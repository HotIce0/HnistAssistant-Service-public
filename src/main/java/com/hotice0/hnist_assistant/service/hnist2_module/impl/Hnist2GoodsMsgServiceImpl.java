package com.hotice0.hnist_assistant.service.hnist2_module.impl;

import com.hotice0.hnist_assistant.db.mapper.Hnist2GoodsMsgMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsg;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsMsgView;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-22 11:39
 */
@Service
public class Hnist2GoodsMsgServiceImpl implements Hnist2GoodsMsgService {
    @Autowired
    Hnist2GoodsMsgMapper hnist2GoodsMsgMapper;

    @Override
    public void leaveGoodsMsg(Integer goods_id, Integer send_user_id, Integer msg_id, String content) throws HAException {
        if (msg_id != null) {
            Hnist2GoodsMsg hnist2GoodsMsg = getByID(msg_id);
            if (!hnist2GoodsMsg.getGoods_id().equals(goods_id)) {
                throw new HAException(HAError.HNIST2_GOODS_MSG_REPLY_NOT_EXIST);
            }
            if (hnist2GoodsMsg.getSend_user_id().equals(send_user_id)) {
                throw new HAException(HAError.HNIST2_CANNOT_LEAVE_SELF);
            }
        }
        Hnist2GoodsMsg hnist2GoodsMsgNew = new Hnist2GoodsMsg(
                goods_id, send_user_id, msg_id, content
        );
        hnist2GoodsMsgMapper.insert(hnist2GoodsMsgNew);
    }

    @Override
    public Hnist2GoodsMsg getByID(Integer goods_msg_id) throws HAException {
        Hnist2GoodsMsg hnist2GoodsMsg = hnist2GoodsMsgMapper.findByID(goods_msg_id);
        if (hnist2GoodsMsg == null) {
            throw new HAException(HAError.HNIST2_GOODS_MSG_NOT_EXIST);
        }
        return hnist2GoodsMsg;
    }

    @Override
    public List<Hnist2GoodsMsgView> getMsgsByGoodsID(Integer goods_id) {
        return hnist2GoodsMsgMapper.getMsgsByGoodsID(goods_id);
    }

    @Override
    public void deleteGoodsMsg(Integer msg_id, Integer hnist2_id) throws HAException {
        int affectRow = hnist2GoodsMsgMapper.deleteByIDAuthWithHnist2ID(msg_id, hnist2_id);
        if (affectRow < 1) {
            throw new HAException(HAError.HNIST2_GOODS_MSG_NOT_EXIST);
        }
    }
}
