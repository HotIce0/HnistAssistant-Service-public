package com.hotice0.hnist_assistant.service.basic_module;

import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.db.model.BasicConfig;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-29 17:41
 */
public interface BasicConfigService {
    List<BasicConfig> getConfigByAppName(JSONArray app_namesJSONArray);
}
