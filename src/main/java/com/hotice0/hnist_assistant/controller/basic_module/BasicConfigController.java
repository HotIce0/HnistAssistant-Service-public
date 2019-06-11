package com.hotice0.hnist_assistant.controller.basic_module;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.annotation.BasicLoginAuth;
import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.BasicConfigService;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @Author HotIce0
 * @Create 2019-05-29 17:28
 */
@RestController
@RequestMapping(value = "/basic/config")
@Validated
public class BasicConfigController extends BaseController {
    @Autowired
    BasicConfigService basicConfigService;
    /**
     * 获取所有配置文件
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResult get(
            @NotBlank(message = "app_names不能为空")
            String app_names
    ) throws HAException {
        JSONArray jsonArrayAppnames = null;
        try {
            jsonArrayAppnames = JSON.parseArray(app_names);
        } catch (Exception e) {
            throw new HAException(HAError.PARAMENT_INVALID.setErrMsg("app_names参数无效"));
        }
        return CommonResult.success(basicConfigService.getConfigByAppName(jsonArrayAppnames));
    }
}
