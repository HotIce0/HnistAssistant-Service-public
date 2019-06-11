package com.hotice0.hnist_assistant.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志记录中间件
 * @Author HotIce0
 * @Create 2019-03-31 22:39
 */
@Aspect
@Order(100)
@Component
public class HttpLogAspect {
    private final static Logger logger = LoggerFactory.getLogger(HttpLogAspect.class);

    //匹配controller包及其子包下所有public方法(切点内的东西并不会执行)
    @Pointcut("execution(* com.hotice0.hnist_assistant.controller..*.*(..)) && !execution(* com.hotice0.hnist_assistant.controller.BaseController.*(..))")
    public void httpRequest(){ }


    @Before("httpRequest()")
    public void log(JoinPoint joinPoint){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();

        // log
        logger.info("url={}, method={}, ip={}, class_method={}, args={}",
                httpServletRequest.getRequestURL(),
                httpServletRequest.getMethod(),
                httpServletRequest.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                joinPoint.getArgs()
                );
    }
}
