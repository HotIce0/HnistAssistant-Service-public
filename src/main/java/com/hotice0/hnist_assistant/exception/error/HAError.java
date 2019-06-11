package com.hotice0.hnist_assistant.exception.error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author HotIce0
 * @Create 2019-04-15 11:58
 */
@NoArgsConstructor
@AllArgsConstructor
public enum HAError implements HAErrorInterface {
    // 1000 未知错误
    UNKNOWN_ERROR(10001, "未知错误"),
    // 2000 系统通用基础错误
    MISSION_PARAMENT(20001, "缺少参数"),
    PARAMENT_INVALID(20002, "无效参数"),
    PARAMENT_AUTH_FAIL(20003, "参数验证失败"),
    OPEN_ID_GET_FAIL(20004, "该小程序未添加到微信开放平台"),
    UPLOAD_IMG_TYPE_NOT_SUPPORT(20005, "上传的图片格式不支持"),
    FILEUPLOAD_FAIL(20006, "文件上传失败"),
    FILE_NOT_EXIST(20007, "文件不存在请重新上传"),
    FILE_URL_PARSE_FAIL(20008, "URL解析失败"),
    FILE_URL_NOT_CONTAIN_FILE(20009, "URL不包含文件"),
    FILE_UPLOAD_CANNOT_EMPTY(200010, "不可上传空文件"),
    // 3000 用户模块错误
    LOGIN_CHECK_USERINFO_FAIL(30001, "登录用户信息校验失败"),
    WX_LOGIN_FAIL(30002, "微信登录失败"),
    INVALIED_LOGIN_STATUS(30003, "登录状态无效"),
    PERMISSION_DENIED(30004, "权限不足"),
    USER_NOT_EXIST(30005, "用户不存在"),
    ROLE_INFO_NOT_EXIST(30006, "留言信息不存在"),
    USER_ROLE_UPDATE_FAIL(30007, "用户角色授予失败"),
    // 4000 Hnist2 模块
    HNIST2_STU_UNAUTH(40001, "未进行学生认证"),
    HNIST2_STU_AUTH_FAIL(40002, "学生认证失败,学生信息有误"),
    HNIST2_STU_AUTH_FAIL_NETWORK(40003, "信息认证时服务器网络异常，认证失败，请稍后再试"),
    HNIST2_USER_NOT_EXIST(40004, "Hnist2用户不存在"),
    HNSIT2_GOODS_CREATE(40005, "商品上架失败"),
    HNIST2_GOODS_NOT_EXIST(40006, "商品信息不存在"),
    HNIST2_OP_PERMISSION_DENIED(40007, "无权操作该商品"),
    HNIST2_GOODS_ALERT_FAIL(40008, "商品信息修改失败"),
    HNIST2_GOODS_MSG_NOT_EXIST(40009, "商品留言不存在"),
    HNIST2_GOODS_REPLY_PERMISSION_DENIED(400010, "无权限回复留言"),
    HNIST2_GOODS_MSG_REPLY_NOT_EXIST(400011, "回复的留言不存在"),
    HNIST2_GOODS_FREE_REQUIRE_CANNOT_EMPYTY(400012, "免费送的要求不可以为空"),
    HNIST2_COLLECTION_ITEM_NOT_EXIST(400013, "收藏记录不存在"),
    HNIST2_COLLECTION_ITEM_EXIST(400014, "商品已经收藏"),
    HNIST2_COLLECTION_CANCEL_FAIL(400015, "未知异常,商品收藏取消失败"),
    HNIST2_GOODS_COLLECTION_DECREASE_FAIL(400016, "商品收藏数减少失败"),
    HNIST2_GOODS_COLLECTION_ADD_FAIL(400017, "商品收藏数增加失败"),
    HNIST2_FOLLOW_EXIST(400018, "已经关注"),
    HNIST2_FOLLOW_CANCELED(400019, "关注记录不存在"),
    HNIST2_FOLLOW_FAIL_UNKNOW_ERROR(400020, "关注失败未知错误"),
    HNIST2_CANNOT_LEAVE_SELF(400021, "不可以回复自己"),
    HNIST2_CANNOT_FOLLOW_SELF(400022, "不可以关注自己"),
    HNIST2_GOODS_DELETE_FAIL(400023, "商品失败"),
    ;
    private int code;
    private String msg;


    @Override
    public int getErrCode() {
        return this.code;
    }

    @Override
    public String getErrMsg() {
        return this.msg;
    }

    @Override
    public HAError setErrMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
