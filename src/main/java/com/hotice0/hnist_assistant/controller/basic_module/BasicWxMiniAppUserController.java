package com.hotice0.hnist_assistant.controller.basic_module;

import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.service.basic_module.BasicUserService;
import com.hotice0.hnist_assistant.service.basic_module.impl.BasicUserServiceImpl;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * 微信小程序用户控制器
 *
 * @Author HotIce0
 * @Create 2019-04-02 20:22
 */
@RestController
@RequestMapping(value = "/wx/miniapp/user")
@Validated
public class BasicWxMiniAppUserController extends BaseController {
    @Autowired
    private BasicUserService basicUserService;

    /**
     * 登录
     *
     * @param code
     * @param signature
     * @param rawData
     * @param encryptedData
     * @param iv
     * @return 返回session_id
     * @throws HAException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(
            @NotBlank(message = "code不可为空")
                    String code,
            @NotBlank(message = "signature不可为空")
                    String signature,
            @NotBlank(message = "rawData不可为空")
                    String rawData,
            @NotBlank(message = "encryptedData不可为空")
                    String encryptedData,
            @NotBlank(message = "iv不可为空")
                    String iv
    ) throws HAException {
        return CommonResult.success(basicUserService.miniprogramLogin(code, signature, rawData, encryptedData, iv));
    }

    /**
     * 测试登录
     * @param request
     * @return
     * @throws HAException
     */
    @RequestMapping(value = "/testLogin", method = RequestMethod.POST)
    public CommonResult testLogin(
            HttpServletRequest request
    ) throws HAException {
        request.getSession().setAttribute("uuid", 72);
        request.getSession().setAttribute("role_id", 2);
        return CommonResult.success("测试登录成功");
    }
}
