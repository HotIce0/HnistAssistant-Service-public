package com.hotice0.hnist_assistant.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-04-02 09:10
 */
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
@Data
public class MiniProgramConfigEntity {
    /**
     * 设置微信小程序的appid
     */
    private String appid;

    /**
     * 设置微信小程序的Secret
     */
    private String secret;

    /**
     * 设置微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;
}
