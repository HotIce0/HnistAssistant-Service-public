package com.hotice0.hnist_assistant.utils.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author HotIce0
 * @Create 2019-05-07 21:01
 */
@Data
public class ValidationResult {
    // 是否有错误
    private boolean hasError = false;
    // 错误信息存储
    private Map<String, String> errorMsgMap = new HashMap<String, String>();
    public String getErrorMsg() {
        return StringUtils.join(this.errorMsgMap.values().toArray(), ",");
    }
}
