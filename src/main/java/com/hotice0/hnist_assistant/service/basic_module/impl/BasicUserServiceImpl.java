package com.hotice0.hnist_assistant.service.basic_module.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.hotice0.hnist_assistant.config.MiniProgramConfiguration;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.mapper.BasicUsersMapper;
import com.hotice0.hnist_assistant.db.model.BasicRole;
import com.hotice0.hnist_assistant.db.model.BasicUser;
import com.hotice0.hnist_assistant.service.basic_module.BasicUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HotIce0
 * @Create 2019-04-01 11:12
 */
@Service
public class BasicUserServiceImpl implements BasicUserService {
    private final static Logger logger = LoggerFactory.getLogger(BasicUserServiceImpl.class);
    @Autowired
    HttpServletRequest request;
    @Autowired
    private BasicUsersMapper basicUserMapper;

    /**
     * 小程序登录请求处理方法
     * @param code
     * @param signature
     * @param rawData
     * @param encryptedData
     * @param iv
     * @return
     * @throws HAException
     */
    public Map<String, String> miniprogramLogin(String code, String signature, String rawData, String encryptedData, String iv) throws HAException {
        // 获取微信小程序SDK服务对象
        final WxMaService service = MiniProgramConfiguration.getWxMaService();

        try {
            WxMaJscode2SessionResult session = service.getUserService().getSessionInfo(code);
            String sessionKey = session.getSessionKey();
            // 用户信息校验
            if (!service.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
                throw new HAException(HAError.LOGIN_CHECK_USERINFO_FAIL);
            }

            // 解密用户信息
            WxMaUserInfo userInfo = service.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            String union_id = userInfo.getUnionId();
            if (union_id == null) {
                throw new HAException(HAError.OPEN_ID_GET_FAIL);
            }
            // 用户信息存储至，数据库
            BasicUser user = new BasicUser(
                    BasicRole.BASIC_ROLE_USER,
                    userInfo.getNickName(),
                    userInfo.getAvatarUrl(),
                    userInfo.getOpenId(),
                    union_id
                    );

            BasicUser basicUser = basicUserMapper.findByUNIONID(union_id);
            if (basicUser == null){
                // 用户不存在，需要新建用户信息
                basicUserMapper.insert(user);
                // 查询用户信息
                basicUser = basicUserMapper.findByUNIONID(userInfo.getUnionId());
            }

            // 产生session会话信息
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("uuid", basicUser.getUuid());
            httpSession.setAttribute("role_id", basicUser.getRole_id());

            Map<String, String> mapResult = new HashMap<>();
            mapResult.put("skey", httpSession.getId());
            return mapResult;
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            throw new HAException(HAError.WX_LOGIN_FAIL);
        }
    }

    @Override
    public BasicUser getBasicUserByUUID(Integer uuid) throws HAException {
        BasicUser basicUser = basicUserMapper.findByUUID(uuid);
        if (basicUser == null) {
            throw new HAException(HAError.USER_NOT_EXIST);
        }
        return basicUser;
    }

    /**
     * 更新用户的角色
     * @param uuid
     * @param role_id
     */
    @Override
    public void updateBasicUserRoleID(Integer uuid, Integer role_id) throws HAException {
        int affectRow = basicUserMapper.updateRoleID(uuid, role_id);
        if (affectRow < 1) {
            throw new HAException(HAError.USER_ROLE_UPDATE_FAIL);
        }
    }
}
