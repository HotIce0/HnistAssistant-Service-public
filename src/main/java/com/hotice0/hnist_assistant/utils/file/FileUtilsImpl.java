package com.hotice0.hnist_assistant.utils.file;

import com.hotice0.hnist_assistant.config.FileUploadImgConfigEntity;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author HotIce0
 * @Create 2019-05-24 16:15
 */
@Component
public class FileUtilsImpl implements FileUtils {
    @Autowired
    FileUploadImgConfigEntity fileUploadImgConfigEntity;
    @Override
    public File getFileByName(String filename) throws HAException {
        String localpath = fileUploadImgConfigEntity.getPath() + "/" + filename;
        File file = new File(localpath);
        if (!file.exists()) {
            throw new HAException(HAError.FILE_NOT_EXIST.setErrMsg("文件不存在:" + filename));
        }
        return file;
    }
}
