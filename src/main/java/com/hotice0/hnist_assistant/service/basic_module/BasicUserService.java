package com.hotice0.hnist_assistant.service.basic_module;

import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.model.BasicUser;

import java.util.Map;

/**
 * @Author HotIce0
 * @Create 2019-05-19 10:22
 */
public interface BasicUserService {
    Map<String, String> miniprogramLogin(String code, String signature, String rawData, String encryptedData, String iv) throws HAException;
    BasicUser getBasicUserByUUID(Integer uuid) throws HAException;
    void updateBasicUserRoleID(Integer uuid, Integer role_id) throws HAException;
}
