package com.hotice0.hnist_assistant.service.basic_module.impl;

import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.mapper.BasicRoleMapper;
import com.hotice0.hnist_assistant.db.model.BasicRole;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.BasicRoleService;
import com.hotice0.hnist_assistant.utils.cache.CacheConstant;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * @Author HotIce0
 * @Create 2019-05-19 11:12
 */
@Service
public class BasicRoleServiceImpl implements BasicRoleService {
    @Autowired
    BasicRoleMapper basicRoleMapper;
    @Autowired
    BasicRoleService basicRoleService;

    @Cacheable(value = CacheConstant.BASIC_ROLE_CACHE_VALUE, key = CacheConstant.CACHE_KEY_GENERATE_BY_CACHENAME_AND_PARAMENT_ONE)
    @Override
    public BasicRole getByID(Integer role_id) throws HAException {
        BasicRole basicRole = basicRoleMapper.findByID(role_id);
        if (basicRole == null) {
            throw new HAException(HAError.ROLE_INFO_NOT_EXIST);
        }
        return basicRole;
    }

    @Override
    public boolean hasPermission(Integer role_id, int[] permissions) throws HAException {
        BasicRole basicRole = basicRoleService.getByID(role_id);
        return basicRole.hasPermission(permissions);
    }
}
