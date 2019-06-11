package com.hotice0.hnist_assistant.utils.oss;

/**
 * @Author HotIce0
 * @Create 2019-05-24 11:43
 */

import com.alibaba.fastjson.JSONArray;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.hotice0.hnist_assistant.config.AliyunOSSConfigEntity;
import com.hotice0.hnist_assistant.config.FileUploadImgConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 将文件上传至云存储
 */
@Component
public class OSSUtilsImpl implements OSSUtils{
    @Autowired
    AliyunOSSConfigEntity aliyunOSSConfigEntity;
    @Override
    public void uploadFile(String oss_path, File file) {
        OSS ossClient = new OSSClient(
                aliyunOSSConfigEntity.getEndpoint(),
                aliyunOSSConfigEntity.getAccessKeyId(),
                aliyunOSSConfigEntity.getAccessKeySecret()
        );
        ossClient.putObject(new PutObjectRequest(aliyunOSSConfigEntity.getBucketName(), oss_path + "/" + file.getName(), file));
        ossClient.shutdown();
    }

    @Override
    public void delFile(String oss_path, JSONArray filenames) {
        OSS ossClient = new OSSClient(
                aliyunOSSConfigEntity.getEndpoint(),
                aliyunOSSConfigEntity.getAccessKeyId(),
                aliyunOSSConfigEntity.getAccessKeySecret()
        );

        // 删除文件。
        List<String> keysFilename = filenames.toJavaList(String.class);
        List<String> keys = new ArrayList<>();
        for (String filename: keysFilename){
            keys.add(oss_path + "/" + filename);
        }
        //DeleteObjectsResult deleteObjectsResult =
        ossClient.deleteObjects(new DeleteObjectsRequest(aliyunOSSConfigEntity.getBucketName()).withKeys(keys));
//        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
