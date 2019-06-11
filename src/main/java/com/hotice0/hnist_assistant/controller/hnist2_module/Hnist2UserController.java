package com.hotice0.hnist_assistant.controller.hnist2_module;

import com.hotice0.hnist_assistant.annotation.BasicLoginAuth;
import com.hotice0.hnist_assistant.annotation.Hnist2BindAuth;
import com.hotice0.hnist_assistant.annotation.PermissionAuth;
import com.hotice0.hnist_assistant.controller.BaseController;
import com.hotice0.hnist_assistant.db.model.BasicPermission;
import com.hotice0.hnist_assistant.db.model.Hnist2Follow;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.model.Hnist2User;
import com.hotice0.hnist_assistant.controller.model_view.hnist2_model_view.MVStudentInfo;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2UserService;
import com.hotice0.hnist_assistant.utils.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-04-08 12:44
 */
@RestController
@RequestMapping(value = "/app/hnist2/user")
@Validated
public class Hnist2UserController extends BaseController {
    @Autowired
    Hnist2UserService hnist2UserService;

    /***
     * 学生认证
     * @param name
     * @param cardID
     * @param gender
     * @param studentID
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @PermissionAuth(name = "可以学生认证的权限", value = {BasicPermission.BASIC_CAN_STU_AUTH})
    public CommonResult auth(
            @RequestParam String name,
            @RequestParam String cardID,
            @RequestParam String gender,
            @RequestParam String studentID
    ) throws HAException {
        hnist2UserService.bindStudentID(name, cardID, gender, studentID);
        return CommonResult.success("学生认证成功");
    }

    /**
     * 获取学生认证信息
     *
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @RequestMapping(value = "/getStuInfo", method = RequestMethod.POST)
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    public CommonResult getStuInfo(
            HttpServletRequest request
    ) throws HAException {
        HttpSession httpSession = request.getSession();
        Integer uuid = (Integer) httpSession.getAttribute("uuid");
        Hnist2User hnist2User = hnist2UserService.getHnist2UserByUUID(uuid);
        // 更新上次登录时间
        hnist2UserService.updateLastLoginAt(hnist2User.getId());
        MVStudentInfo mvStudentInfo = MVStudentInfo.valueOfHnist2User(hnist2User);
        return CommonResult.success(mvStudentInfo);
    }


    /**
     * 关注用户
     * @param request
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public CommonResult follow(
            HttpServletRequest request,
            @NotNull(message = "关注用户id不能为空")
            Integer hnist2_id
    ) throws HAException{
        Integer fans_hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        // 不可以关注自己
        if (fans_hnist2_id.equals(hnist2_id)) {
            throw new HAException(HAError.HNIST2_CANNOT_FOLLOW_SELF);
        }
        hnist2UserService.follow(fans_hnist2_id, hnist2_id);
        return CommonResult.success("关注成功");
    }

    /**
     * 取消关注
     * @param request
     * @param hnist2_id
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/cancelFollow", method = RequestMethod.POST)
    public CommonResult cancelFollow(
            HttpServletRequest request,
            @NotNull(message = "关注用户id不能为空")
            Integer hnist2_id
    ) throws HAException {
        Integer fans_hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        hnist2UserService.cancelFollow(fans_hnist2_id, hnist2_id);
        return CommonResult.success("关注取消成功");
    }


    /**
     * 获取关注的人的列表
     * @param request
     * @return
     * @throws HAException
     */
    @BasicLoginAuth
    @Hnist2BindAuth
    @PermissionAuth(name = "HNIST2权限", value = {BasicPermission.HNIST2_PERMISSION})
    @RequestMapping(value = "/getFollows", method = RequestMethod.POST)
    public CommonResult getFollows(
            HttpServletRequest request
    ) throws HAException {
        Integer fans_hnist2_id = (Integer) request.getSession().getAttribute("hnist2_id");
        List<Hnist2Follow> hnist2FollowList = hnist2UserService.getFollows(fans_hnist2_id);
        return CommonResult.success(hnist2FollowList);
    }
}