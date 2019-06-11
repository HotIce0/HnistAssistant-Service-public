package com.hotice0.hnist_assistant.db.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Hotice0
 */
@Data
@NoArgsConstructor
public class BasicRole implements Serializable {
    // 游客
    public static final int BASIC_ROLE_USER = 1;
    // 已经学生认证的普通账号
    public static final int STU_AUTH_ROLE_USER = 2;

    private Integer id;
    private String role_name;
    private String role_permission;
    private Timestamp created_at;
    private Timestamp updated_at;

    public boolean hasPermission(int[] permissions) {
        return BasicRole.hasPermission(this.role_permission, permissions);
    }

    public static boolean hasPermission(String role_permission, int[] permissions) {
        Integer [] ioArr = ArrayUtils.toObject(permissions);
        List<Integer> permissionsList = Arrays.asList(ioArr);
        List<Integer> rolePermissionsList = JSON.parseArray(role_permission).toJavaList(Integer.class);
        if (rolePermissionsList.containsAll(permissionsList)) {
            return true;
        } else {
            return false;
        }
    }
}
