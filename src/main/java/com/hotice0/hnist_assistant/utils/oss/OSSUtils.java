package com.hotice0.hnist_assistant.utils.oss;

import com.alibaba.fastjson.JSONArray;

import java.io.File;

/**
 * @Author HotIce0
 * @Create 2019-05-24 15:43
 */
public interface OSSUtils {
    void uploadFile(String oss_path, File file);
    public void delFile(String oss_path, JSONArray filenames);
}
