package com.hotice0.hnist_assistant.aspect;

import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 微信小程序登录认证中间件
 * @Author HotIce0
 * @Create 2019-04-10 14:56
 */
@Aspect
@Order(200)
@Component
public class BasicLoginAuthAspect {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.hotice0.hnist_assistant.annotation.BasicLoginAuth)")
    public void needAuthRequest(){ }

    /***
     * 基础用户登录认证
     * @param joinPoint
     * @throws HAException
     */
    @Before("needAuthRequest()")
    public void auth(JoinPoint joinPoint) throws HAException {
        HttpSession httpSession = request.getSession();
        List<String> keysArr = Collections.list(httpSession.getAttributeNames());
        List<String> keysTemp = Arrays.asList("uuid", "role_id");
        if (!keysArr.containsAll(keysTemp)) {
            throw new HAException(HAError.INVALIED_LOGIN_STATUS);
        }
    }
}
