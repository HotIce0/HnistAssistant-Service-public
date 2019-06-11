package com.hotice0.hnist_assistant.controller.basic_module;

import com.hotice0.hnist_assistant.annotation.BasicLoginAuth;
import com.hotice0.hnist_assistant.annotation.PermissionAuth;
import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.db.model.BasicPermission;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.basic_module.FileUploadService;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HotIce0
 * @Create 2019-05-23 17:26
 */
@RestController
@RequestMapping(value = "/file")
public class FileController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileUploadService fileUploadService;

    @BasicLoginAuth
    @PermissionAuth(name = "图片上传的权限", value = {BasicPermission.BASIC_UPLOAD_IMG_PERMISSION})
    @RequestMapping(value = "/insertPicture", method = RequestMethod.POST)
    public CommonResult insertPicture(
            MultipartFile file
    ) throws HAException {
        if (file.getSize() == 0) {
            throw new HAException(HAError.FILE_UPLOAD_CANNOT_EMPTY);
        }
        Map<String, String> mapResult = new HashMap<>();
        String fileName = fileUploadService.uploadImg(file);
        mapResult.put("filename", fileName);
        return CommonResult.success(mapResult);
    }
}
