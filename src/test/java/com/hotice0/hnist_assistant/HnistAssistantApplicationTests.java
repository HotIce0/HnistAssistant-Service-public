package com.hotice0.hnist_assistant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotice0.hnist_assistant.config.AliyunOSSConfigEntity;
import com.hotice0.hnist_assistant.config.FileUploadImgConfigEntity;
import com.hotice0.hnist_assistant.controller.hnist2_module.Hnist2GoodsController;
import com.hotice0.hnist_assistant.db.model.BasicRole;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.service.basic_module.FileUploadService;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HnistAssistantApplicationTests {
    @Test
    public void contextLoads() throws HAException {
    }
}
