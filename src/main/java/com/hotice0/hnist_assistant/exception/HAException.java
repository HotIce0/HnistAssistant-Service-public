package com.hotice0.hnist_assistant.exception;

import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.error.HAErrorInterface;

/**
 * @Author HotIce0
 * @Create 2019-05-04 17:20
 */
public class HAException extends Exception implements HAErrorInterface {
    private HAError haError;

    public HAException(HAError haError) {
        super();
        this.haError = haError;
    }

    @Override
    public int getErrCode() {
        return this.haError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.haError.getErrMsg();
    }

    @Override
    public HAError setErrMsg(String msg) {
        return this.haError.setErrMsg(msg);
    }
}
