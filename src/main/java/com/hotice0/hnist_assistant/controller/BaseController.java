package com.hotice0.hnist_assistant.controller;

import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import com.hotice0.hnist_assistant.utils.validator.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 通用异常处理
 *
 * @Author HotIce0
 * @Create 2019-05-04 17:16
 */
@Controller
public class BaseController {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex) {
        Map<String, Object> dataMap = new HashMap<>();
        // 未知异常
        dataMap.put("errCode", HAError.UNKNOWN_ERROR.getErrCode());
        dataMap.put("errMsg", HAError.UNKNOWN_ERROR.getErrMsg() + ":" + ex.getMessage());
        ex.printStackTrace();
        return CommonResult.fail(dataMap);
    }

    /**
     * 内部异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HAException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object handleEMStoreException(HttpServletRequest request, HAException ex) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("errCode", ex.getErrCode());
        dataMap.put("errMsg", ex.getErrMsg());
        return CommonResult.fail(dataMap);
    }

    /**
     * 参数验证异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        Map<String, Object> dataMap = new HashMap<>();
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        constraintViolationSet.forEach(constraintViolation -> {
            String propertyName = constraintViolation.getPropertyPath().toString();
            String errMsg = constraintViolation.getMessage();
            validationResult.getErrorMsgMap().put(propertyName, propertyName + ":" + errMsg);
        });
        dataMap.put("errCode", HAError.PARAMENT_AUTH_FAIL.getErrCode());
        dataMap.put("errMsg", HAError.PARAMENT_AUTH_FAIL.getErrMsg() + ":" + validationResult.getErrorMsg());
        return CommonResult.fail(dataMap);
    }

}
