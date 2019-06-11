package com.hotice0.hnist_assistant.service.hnist2_module.impl;

import com.hotice0.hnist_assistant.db.mapper.Hnist2GoodsTypeMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2GoodsType;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2GoodsTypeService;
import com.hotice0.hnist_assistant.utils.cache.CacheConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-04-09 14:37
 */
@Service
public class Hnist2GoodsTypeServiceImpl implements Hnist2GoodsTypeService {
    @Autowired
    Hnist2GoodsTypeMapper hnist2GoodsTypeMapper;


    @Cacheable(value = CacheConstant.HNIST2_GOODS_TYPE_CACHE_VALUE, key = CacheConstant.CACHE_KEY_GENERATE_BY_CACHENAME)
    @Override
    public List<Hnist2GoodsType> getAll() {
        return hnist2GoodsTypeMapper.getAll();
    }

    @CacheEvict(value = CacheConstant.HNIST2_GOODS_TYPE_CACHE_VALUE, key = CacheConstant.CACHE_KEY_GENERATE_BY_CACHENAME)
    @Override
    public void add(String name) {
        Hnist2GoodsType hnist2GoodsType = new Hnist2GoodsType();
        hnist2GoodsType.setType_name(name);
        hnist2GoodsTypeMapper.insert(hnist2GoodsType);
    }

    @CacheEvict(value = CacheConstant.HNIST2_GOODS_TYPE_CACHE_VALUE, key = CacheConstant.CACHE_KEY_GENERATE_BY_CACHENAME)
    @Override
    public void del(Integer id) {
        hnist2GoodsTypeMapper.deleteByID(id);
    }

    @CacheEvict(value = CacheConstant.HNIST2_GOODS_TYPE_CACHE_VALUE, key = CacheConstant.CACHE_KEY_GENERATE_BY_CACHENAME)
    @Override
    public void update(Integer id, String name) {
        Hnist2GoodsType hnist2GoodsType = new Hnist2GoodsType();
        hnist2GoodsType.setId(id);
        hnist2GoodsType.setType_name(name);
        hnist2GoodsTypeMapper.update(hnist2GoodsType);
    }
}
