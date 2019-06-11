package com.hotice0.hnist_assistant.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author HotIce0
 * @Create 2019-05-24 15:23
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliyunOSSConfigEntity{
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
