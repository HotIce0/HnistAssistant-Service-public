package com.hotice0.hnist_assistant.service.basic_module.impl;

import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.db.mapper.BasicConfigMapper;
import com.hotice0.hnist_assistant.db.model.BasicConfig;
import com.hotice0.hnist_assistant.service.basic_module.BasicConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-29 17:41
 */
@Service
public class BasicConfigServiceImpl implements BasicConfigService {
    @Autowired
    BasicConfigMapper basicConfigMapper;
    @Override
    public List<BasicConfig> getConfigByAppName(JSONArray app_namesJSONArray) {
        String app_names = StringUtils.join(app_namesJSONArray, '|');
        return basicConfigMapper.getByAppName(app_names);
    }
}
