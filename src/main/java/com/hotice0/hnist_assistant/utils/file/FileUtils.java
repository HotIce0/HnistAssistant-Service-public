package com.hotice0.hnist_assistant.utils.file;

import com.hotice0.hnist_assistant.exception.HAException;

import java.io.File;

/**
 * @Author HotIce0
 * @Create 2019-05-24 16:15
 */
public interface FileUtils {
    public File getFileByName(String filename) throws HAException;
}
