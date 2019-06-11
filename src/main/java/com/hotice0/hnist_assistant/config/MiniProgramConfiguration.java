package com.hotice0.hnist_assistant.config;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author HotIce0
 * @Create 2019-04-02 18:51
 */
@Configuration
public class MiniProgramConfiguration {
    // 微信小程序配置文件实体对象
    @Autowired
    private MiniProgramConfigEntity miniProgramConfigEntity;
    @Getter
    private static WxMaService wxMaService;
    // 暂时不用，需要用到模版消息的时候再用
    @Getter
    private static WxMaMessageRouter wxMaMessageRouter;

    /**
     * 初始化服务器API对象(该Bean载入容器时被调用)
     */
    @PostConstruct
    public void init(){
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(miniProgramConfigEntity.getAppid());
        config.setSecret(miniProgramConfigEntity.getSecret());
        config.setToken(miniProgramConfigEntity.getToken());
        config.setAesKey(miniProgramConfigEntity.getAesKey());
        config.setMsgDataFormat(miniProgramConfigEntity.getMsgDataFormat());

        wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);
    }
}
