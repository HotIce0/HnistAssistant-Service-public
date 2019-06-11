package com.hotice0.hnist_assistant.db.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author Hotice0
 */
@Data
@NoArgsConstructor
public class BasicPermission  implements Serializable {
    // 基础用户模块 1000
    public final static int BASIC_UPLOAD_IMG_PERMISSION = 10000;
    public final static int BASIC_CAN_STU_AUTH = 10001;
    // hnist2模块 2000
    public final static int HNIST2_PERMISSION = 20000;
    public final static int HNIST2_PUBLISH_GOODS_PERMISSION = 20001;
    public final static int HNIST2_LEAVE_GOODS_MSG_PERMISSION = 20002;

    public final static int HNIST2_GOODS_TYPE_ADMIN = 20003; // 管理员的权限

    private Integer id;
    private String permission_no;
    private String permission_name;
    private Timestamp created_at;
    private Timestamp updated_at;
}
