package com.hotice0.hnist_assistant.utils.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author HotIce0
 * @Create 2019-04-15 11:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    String status;
    Object data;

    public static CommonResult success(Object data) {
        return new CommonResult("success", data);
    }

    public static CommonResult fail(Object data) {
        return new CommonResult("fail", data);
    }
}
