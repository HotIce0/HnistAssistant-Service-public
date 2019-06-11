package com.hotice0.hnist_assistant.annotation;

import java.lang.annotation.*;

/**
 * @Author HotIce0
 * @Create 2019-04-11 10:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionAuth {
    int [] value();
    String name();
}
