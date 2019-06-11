package com.hotice0.hnist_assistant.service.hnist2_module.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.db.mapper.Hnist2FollowMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2FollowGoods;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsDetailView;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.mapper.Hnist2GoodsMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2Goods;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.FileUploadService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsService;
import com.hotice0.hnist_assistant.utils.cache.CacheConstant;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-04-09 14:52
 */
@Service
public class Hnist2GoodsServiceImpl implements Hnist2GoodsService {
    @Autowired
    Hnist2GoodsMapper hnist2GoodsMapper;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    Hnist2FollowMapper hnist2FollowMapper;
//    @Cacheable(
//            value = CacheConstant.HNIST2_GOODS_DETAIL_GET_BY_GOODS_TYPE,
//            key = "#root.caches[0].name+T(String).valueOf(#p0).concat('-').concat(#p1).concat('-').concat(#p2).concat('-').concat(#p3)"
//    )
    @Override
    public List<Hnist2GoodsDetailView> getByGoodsType(Integer goods_type_id, Integer sort_type, Integer index, Integer pageSize) {
        return hnist2GoodsMapper.getByGoodsType(goods_type_id, sort_type, index, pageSize);
    }

    @Override
    public List<Hnist2Goods> getGoodsByHnist2ID(Integer hnist2_id, Integer index, Integer pageSize) {
        return hnist2GoodsMapper.getGoodsByHnist2ID(hnist2_id, index, pageSize);
    }

    @Override
    public Hnist2Goods getByID(Integer id) throws HAException {
        Hnist2Goods hnist2Goods = hnist2GoodsMapper.findByID(id);
        if (hnist2Goods == null) {
            throw new HAException(HAError.HNIST2_GOODS_NOT_EXIST);
        }
        return hnist2Goods;
    }

    /**
     * 添加新商品
     *
     * @param hnist2Goods
     * @throws HAException
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void add(Hnist2Goods hnist2Goods) throws HAException {
        try {
            hnist2GoodsMapper.insert(hnist2Goods);
            JSONArray jsonArrayPicture = null;
            try{
                jsonArrayPicture = JSON.parseArray(hnist2Goods.getPicture());
            } catch (Exception e){
                throw new HAException(HAError.PARAMENT_INVALID.setErrMsg("picture必须为json数组"));
            }
            // 图片上传到服务器
            hnist2Goods.setPicture(fileUploadService.imgURLUpload(jsonArrayPicture).toJSONString());
            // 将picture更新进数据库
            hnist2GoodsMapper.update(hnist2Goods);
        } catch (Exception ex) {
            throw new HAException(HAError.HNSIT2_GOODS_CREATE.setErrMsg(ex.toString()));
        }
    }

    @Override
    public void del(Hnist2Goods hnist2Goods) throws HAException {
        int affectRow = hnist2GoodsMapper.deleteByID(hnist2Goods.getId());
        if (affectRow < 1) {
            throw new HAException(HAError.HNIST2_GOODS_DELETE_FAIL);
        }
        // 删除云存储上的图片
        JSONArray jsonArray = JSON.parseArray(hnist2Goods.getPicture());
        fileUploadService.delInvaliedFileByURL(jsonArray);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Hnist2Goods hnist2Goods, String originPicture) throws HAException {
        // 上传新上传到的图片
        JSONArray jsonArrayNewJSON = fileUploadService.imgURLUpload(JSON.parseArray(hnist2Goods.getPicture()));
        hnist2Goods.setPicture(jsonArrayNewJSON.toJSONString());
        int affectRow = hnist2GoodsMapper.update(hnist2Goods);
        if (affectRow < 1) {
            throw new HAException(HAError.HNIST2_GOODS_ALERT_FAIL);
        }
        // 查询出需要删除的图片
        JSONArray jsonArrayOriginPicture = JSON.parseArray(originPicture);
        JSONArray jsonArrayInvaliedImgURL = fileUploadService.imgURLFindInvaliedURL(jsonArrayOriginPicture, jsonArrayNewJSON);
        // 将云存储上的应该删除的图片删除
        if (jsonArrayInvaliedImgURL.size() > 0)
            fileUploadService.delInvaliedFileByURL(jsonArrayInvaliedImgURL);
    }

    @Override
    public void visit_amount_add(Integer goods_id) {
        hnist2GoodsMapper.visit_amount_add(goods_id);
    }

    @Override
    public void collection_amount_add(Integer goods_id) throws HAException {
        int affectRow = hnist2GoodsMapper.collection_amount_add(goods_id);
        if (affectRow < 0) {
            throw new HAException(HAError.HNIST2_GOODS_COLLECTION_ADD_FAIL);
        }
    }

    @Override
    public void collection_amount_decrease(Integer goods_id) throws HAException {
        int affectRow = hnist2GoodsMapper.collection_amount_decrease(goods_id);
        if (affectRow < 0) {
            throw new HAException(HAError.HNIST2_GOODS_COLLECTION_DECREASE_FAIL);
        }
    }

    @Override
    public Hnist2GoodsDetailView getGoodsDetailByID(Integer goods_id) throws HAException {
        visit_amount_add(goods_id);
        Hnist2GoodsDetailView hnist2GoodsDetailView = hnist2GoodsMapper.getGoodsDetailByGoodsID(goods_id);
        if (hnist2GoodsDetailView == null) {
            throw new HAException(HAError.HNIST2_GOODS_NOT_EXIST);
        }
        return hnist2GoodsDetailView;
    }

    @Override
    public List<Hnist2GoodsDetailView> getGoodsDetailByIDs(List<Integer> goods_ids) {
        String ids = StringUtils.join(goods_ids, ',');
        return hnist2GoodsMapper.getGoodsDetailByIDs(ids);
    }

    @Override
    public List<Hnist2GoodsDetailView> getFollowGoods(Integer fans_user_id, Integer index, Integer pageSize) {
        return hnist2FollowMapper.getFollowGoodsByFanHnist2ID(fans_user_id, index, pageSize);
    }

    @Override
    public List<Hnist2GoodsDetailView> searchByKeyword(String keyword, Integer index, Integer pageSize) {
        Result result = BaseAnalysis.parse(keyword);
        String searchKeys = result.toStringWithOutNature("|");
        return hnist2GoodsMapper.searchGoodsWithKeyword(searchKeys, index, pageSize);
    }
}
