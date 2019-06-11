package com.hotice0.hnist_assistant.aspect;

import com.hotice0.hnist_assistant.annotation.PermissionAuth;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.model.BasicRole;
import com.hotice0.hnist_assistant.service.basic_module.BasicRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 权限控制中间件
 * @Author HotIce0
 * @Create 2019-04-09 14:56
 */
@Aspect
@Order(400)
@Component
public class PermissionGuardAspect {
    @Autowired
    BasicRoleService basicRoleService;
    @Autowired
    HttpServletRequest request;

    @Pointcut("@annotation(com.hotice0.hnist_assistant.annotation.PermissionAuth)")
    public void needPermissionAuthRequest(){ }

    /**
     * 权限认证
     * @param joinPoint
     * @throws HAException
     */
    @Before("needPermissionAuthRequest()")
    public void permissionAuth(JoinPoint joinPoint) throws HAException {
        HttpSession httpSession = request.getSession();
        Integer role_id = (Integer) httpSession.getAttribute("role_id");
        // 判断该角色是否有所需要的权限
        PermissionAuth permissionAuth = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(PermissionAuth.class);
        int [] permissions = permissionAuth.value();
        if(!basicRoleService.hasPermission(role_id, permissions)){
            throw new HAException(HAError.PERMISSION_DENIED);
        }
    }
}
