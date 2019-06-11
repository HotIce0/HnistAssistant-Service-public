package com.hotice0.hnist_assistant.service.basic_module.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotice0.hnist_assistant.config.FileUploadImgConfigEntity;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.FileUploadService;
import com.hotice0.hnist_assistant.utils.file.FileUtils;
import com.hotice0.hnist_assistant.utils.oss.OSSUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @Author HotIce0
 * @Create 2019-05-24 10:37
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final static Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    @Autowired
    FileUploadImgConfigEntity fileUploadImgConfig;
    @Autowired
    OSSUtils ossUtils;
    @Autowired
    FileUtils fileUtils;

    /**
     * 上传图片至服务器的临时文件夹
     *
     * @param file
     * @return
     * @throws HAException
     */
    @Override
    public String uploadImg(MultipartFile file) throws HAException {
        String[] IMAGE_TYPE = fileUploadImgConfig.getType().split(",");
        boolean flag = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new HAException(HAError.UPLOAD_IMG_TYPE_NOT_SUPPORT);
        } else {
            StringBuilder filenameBuilder = new StringBuilder();
            String timestamps = String.valueOf(new Date().getTime());
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filenameBuilder.append(timestamps);
            filenameBuilder.append(uuid);
            filenameBuilder.append(".");
            // 获得文件类型
            String fileType = file.getContentType();
            // 获得文件后缀名称
            String imageName = fileType.substring(fileType.indexOf("/") + 1);
            filenameBuilder.append(imageName);
            // 上传路径
            String realPath = fileUploadImgConfig.getPath() + "/" + filenameBuilder.toString();
            File dest = new File(realPath);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                throw new HAException(HAError.FILEUPLOAD_FAIL);
            }
            return filenameBuilder.toString();
        }
    }

    /**
     * 将图片文件从临时文件目录上传至OSS云存储
     *
     * @param filename
     * @return
     * @throws HAException
     */
    @Override
    public String uploadImgFileToOSS(String filename) throws HAException {
        File file = fileUtils.getFileByName(filename);
        ossUtils.uploadFile(fileUploadImgConfig.getOssPath(), file);
        return fileUploadImgConfig.getOssHost() + fileUploadImgConfig.getOssPath() + "/" + file.getName();
    }

    /**
     * 从文件URL中提取出文件名
     *
     * @param urlStr
     * @return
     * @throws HAException
     */
    @Override
    public String getFileNameFromUrl(String urlStr) throws HAException {
        try {
            URL fileURL = new URL(urlStr);
            String filename = fileURL.getFile();
            if (filename.length() < 1) {
                throw new HAException(HAError.FILE_URL_NOT_CONTAIN_FILE);
            }
            return filename.substring(filename.lastIndexOf('/') + 1);
        } catch (MalformedURLException e) {
            throw new HAException(HAError.FILE_URL_PARSE_FAIL);
        }
    }

    /**
     * 将数组中需要上传的上传到云存储
     *
     * @param imgArraysJSON
     * @return
     */
    @Override
    public JSONArray imgURLUpload(JSONArray imgArraysJSON) throws HAException {
        JSONArray jsonArrayNew = new JSONArray();
        for (Object url : imgArraysJSON) {
            try {
                // 判断是否是图片URL
                getFileNameFromUrl((String) url);
                jsonArrayNew.add(url);
            } catch (HAException e) {
                // 不是URL说明是要上传的图片
                String fileURL = uploadImgFileToOSS((String) url);
                jsonArrayNew.add(fileURL);
            }
        }
        return jsonArrayNew;
    }

    /**
     * 找出需要删除的URL
     *
     * @param imgArraysOriginJSON
     * @param imgArraysNewJSON
     * @return
     */
    @Override
    public JSONArray imgURLFindInvaliedURL(JSONArray imgArraysOriginJSON, JSONArray imgArraysNewJSON) {
        List<String> imgArraysOrgin = JSONObject.parseArray(imgArraysOriginJSON.toJSONString(), String.class);
        List<String> imgArraysNew = JSONObject.parseArray(imgArraysNewJSON.toJSONString(), String.class);
        Set<String> imgArrayOriginSet = new HashSet<>(imgArraysOrgin);
        Set<String> imgArrayNewSet = new HashSet<>(imgArraysNew);
        imgArrayOriginSet.removeAll(imgArrayNewSet);
        return new JSONArray(Arrays.asList(imgArrayOriginSet.toArray()));
    }

    @Override
    public void delInvaliedFileByURL(JSONArray imgArraysJSON) throws HAException {
        JSONArray jsonArrayInvalied = new JSONArray();
        for (Object url : imgArraysJSON) {
            String filename = getFileNameFromUrl((String) url);
            jsonArrayInvalied.add(filename);
        }
        ossUtils.delFile(fileUploadImgConfig.getOssPath(), jsonArrayInvalied);
    }
}
