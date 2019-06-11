package com.hotice0.hnist_assistant.annotation;

import java.lang.annotation.*;

/**
 * Hnist2是否绑定学生账号注解
 * @Author HotIce0
 * @Create 2019-04-10 20:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Hnist2BindAuth {
}
