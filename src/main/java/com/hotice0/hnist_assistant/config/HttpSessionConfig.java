package com.hotice0.hnist_assistant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * @Author HotIce0
 * @Create 2019-05-06 19:32
 */
// 设置session有效期为1天
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 43200)
public class HttpSessionConfig {
    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy() {
        return new HeaderHttpSessionIdResolver("x-auth-sessionid");
    }
}
