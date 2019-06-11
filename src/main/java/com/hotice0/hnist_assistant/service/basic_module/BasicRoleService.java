package com.hotice0.hnist_assistant.service.basic_module;

import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.model.BasicRole;

/**
 * @Author HotIce0
 * @Create 2019-05-19 11:11
 */
public interface BasicRoleService {
    BasicRole getByID(Integer role_id) throws HAException;

    boolean hasPermission(Integer role_id, int[] permissions) throws HAException;
}
