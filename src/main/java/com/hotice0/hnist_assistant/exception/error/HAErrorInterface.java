package com.hotice0.hnist_assistant.exception.error;

/**
 * @Author HotIce0
 * @Create 2019-04-15 12:02
 */
public interface HAErrorInterface {
    public int getErrCode();
    public String getErrMsg();
    public HAErrorInterface setErrMsg(String msg);
}