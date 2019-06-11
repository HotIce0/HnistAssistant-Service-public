package com.hotice0.hnist_assistant.service.basic_module;

import com.alibaba.fastjson.JSONArray;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author HotIce0
 * @Create 2019-05-24 10:36
 */
public interface FileUploadService {

    String uploadImg(MultipartFile file) throws HAException;

    String uploadImgFileToOSS(String filename) throws HAException;

    String getFileNameFromUrl(String urlStr) throws HAException;

    JSONArray imgURLUpload(JSONArray imgArraysJSON) throws HAException;

    JSONArray imgURLFindInvaliedURL(JSONArray imgArraysOriginJSON, JSONArray imgArraysNewJSON);

    void delInvaliedFileByURL(JSONArray imgArraysJSON) throws HAException;
}
