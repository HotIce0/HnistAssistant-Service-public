package com.hotice0.hnist_assistant.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author HotIce0
 * @Create 2019-05-24 10:34
 */
@Component
@ConfigurationProperties(prefix = "fileupload.img")
@Data
public class FileUploadImgConfigEntity {
    private String path;
    private String type;
    private String ossPath;
    private String ossHost;
}
