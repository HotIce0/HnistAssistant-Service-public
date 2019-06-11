package com.hotice0.hnist_assistant.aspect;

import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.mapper.Hnist2UsersMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 学生认证中间件
 * @Author HotIce0
 * @Create 2019-04-09 15:18
 */
@Aspect
@Order(300)
@Component
public class Hnist2BindAuthAspect {
    @Autowired
    Hnist2UsersMapper hnist2UsersMapper;
    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(com.hotice0.hnist_assistant.annotation.Hnist2BindAuth)")
    public void needBindAuthRequest(){ }

    /**
     * Hnist2学生认证
     * @throws HAException
     */
    @Before("needBindAuthRequest()")
    public void auth() throws HAException {
        HttpSession httpSession = request.getSession();
        // session中是否存在hnist2_id
        Integer hnist2Id = (Integer) httpSession.getAttribute("hnist2_id");
        if (hnist2Id == null){
            // 数据库中查询学生认证信息
            Integer uuid = (Integer) httpSession.getAttribute("uuid");
            Hnist2User hnist2User = hnist2UsersMapper.findByUUID(uuid);
            if (hnist2User == null) {
                // 未进行学生认证
                throw new HAException(HAError.HNIST2_STU_UNAUTH);
            }
            // 将hnist2_id存储起session中保存登录状态
            httpSession.setAttribute("hnist2_id", hnist2User.getId());
        }
    }

}
