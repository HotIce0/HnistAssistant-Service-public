package com.hotice0.hnist_assistant.service.hnist2_module.impl;

import com.hotice0.hnist_assistant.db.mapper.Hnist2FollowMapper;
import com.hotice0.hnist_assistant.db.model.BasicRole;
import com.hotice0.hnist_assistant.db.model.Hnist2Follow;
import com.hotice0.hnist_assistant.db.model.Hnist2FollowGoods;
import com.hotice0.hnist_assistant.exception.error.HAError;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.mapper.Hnist2UsersMapper;
import com.hotice0.hnist_assistant.db.model.Hnist2User;
import com.hotice0.hnist_assistant.service.basic_module.BasicUserService;
import com.hotice0.hnist_assistant.service.hnist2_module.Hnist2UserService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author HotIce0
 * @Create 2019-04-08 12:57
 */
@Service
public class Hnist2UserServiceImpl implements Hnist2UserService {
    private final static Logger logger = LoggerFactory.getLogger(Hnist2UserServiceImpl.class);
    @Autowired
    private Hnist2UsersMapper hnist2UsersMapper;
    @Autowired
    private Hnist2FollowMapper hnist2FollowMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    BasicUserService basicUserService;

    /**
     * 绑定学生账号
     * @param name
     * @param cardID
     * @param gender
     * @param studentID
     * @throws HAException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void bindStudentID(String name, String cardID, String gender, String studentID) throws HAException {
        HttpSession httpSession = request.getSession();
        Integer uuid = (Integer) httpSession.getAttribute("uuid");
        // 学生认证
        Map<String, String> stuInfoMap = null;
        try{
            stuInfoMap = stuAuth(name, cardID, gender, studentID);
        } catch (HAException e) {
            if (e.getErrCode() == HAError.HNIST2_STU_AUTH_FAIL.getErrCode()) {
                // 信息不一致
                if (cardID.substring(cardID.length() - 1, cardID.length()).equals("X")) {
                    // 如果最后一位是大写X
                    // 小写x身份证号码测试
                    stuInfoMap = stuAuth(name, cardID.toLowerCase(), gender, studentID);
                }else {
                    throw e;
                }
            } else {
                throw e;
            }
        }
        // 更新用户角色信息(切换角色至已经认证的学生)
        basicUserService.updateBasicUserRoleID(uuid, BasicRole.STU_AUTH_ROLE_USER);
        // 绑定的数据库操作
        Hnist2User user;
        user = hnist2UsersMapper.findByCardID(cardID);
        if (user == null) {
            // 如果该学生没绑定过该平台, 创建hnist2新用户
            user = new Hnist2User(
                    uuid,
                    stuInfoMap.get("name"),
                    stuInfoMap.get("studentID"),
                    stuInfoMap.get("cardID"),
                    stuInfoMap.get("gender").equals("男") ? 0 : 1,
                    stuInfoMap.get("email")
            );
            hnist2UsersMapper.insert(user);
        } else {
            // 已经绑定过,则将绑定转移到当前微信账号
            Integer uuidOld = user.getUuid();
            if (!uuidOld.equals(uuid)){
                user.setUuid(uuid);
                hnist2UsersMapper.update(user);
            }
        }
        // session无效
        httpSession.invalidate();
    }

    /***
     * 学生认证接口函数
     * @param name
     * @param cardID
     * @param gender
     * @return 学生信息 name,cardID,gender,email
     * @throws HAException
     */
    private Map<String, String> stuAuth(String name, String cardID, String gender, String studentID) throws HAException {
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://uia.hnist.cn/uiauser/patch/selfServiceQuery.action");
            // 设置超时2s
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build();

            httpPost.setConfig(requestConfig);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("domain.name", name));
            nvps.add(new BasicNameValuePair("domain.cardId", cardID));
            nvps.add(new BasicNameValuePair("genders", gender));
            // 0为学生 1为教职工
            nvps.add(new BasicNameValuePair("roleName", "0"));

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream inStream = entity.getContent();
            String content = IOUtils.toString(inStream, "utf-8");
            Document doc = Jsoup.parse(content);
            String studentIDGet = doc.select("#showResult > table > tbody > tr:nth-child(2) > td:nth-child(3)").text();
            String email = doc.select("#showResult > table > tbody > tr:nth-child(2) > td:nth-child(6)").text();
            // 没有查询到学生信息，或者学生的学号与提供的不一致
            if(studentIDGet.length() < 1 || !studentIDGet.equals(studentID)){
                throw new HAException(HAError.HNIST2_STU_AUTH_FAIL);
            }
            Map<String, String> stuinfoMap = new HashMap<>();
            stuinfoMap.put("name", name);
            stuinfoMap.put("studentID", studentIDGet);
            stuinfoMap.put("cardID", cardID);
            stuinfoMap.put("gender", gender);
            stuinfoMap.put("email", email);
            return stuinfoMap;
        }catch (HAException e){
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new HAException(HAError.HNIST2_STU_AUTH_FAIL_NETWORK);
        }
    }

    @Override
    public Hnist2User getHnist2UserByUUID(Integer uuid) throws HAException {
        Hnist2User hnist2User = hnist2UsersMapper.findByUUID(uuid);
        if (hnist2User == null) {
            throw new HAException(HAError.HNIST2_USER_NOT_EXIST);
        }
        return hnist2User;
    }

    @Override
    public void follow(Integer follower_hnist2_id, Integer hnist2_id) throws HAException {
        Hnist2Follow hnist2Follow = new Hnist2Follow();
        hnist2Follow.setFans_user_id(follower_hnist2_id);
        hnist2Follow.setUser_id(hnist2_id);
        try{
            int affectRow = hnist2FollowMapper.insert(hnist2Follow);
            if (affectRow < 1) {
                throw new HAException(HAError.HNIST2_FOLLOW_EXIST);
            }
        } catch (Exception e) {
            if (e instanceof HAException)
                throw (HAException) e;
            else
                throw new HAException(HAError.HNIST2_FOLLOW_FAIL_UNKNOW_ERROR);
        }
    }

    @Override
    public void cancelFollow(Integer follower_hnist2_id, Integer hnist2_id) throws HAException {
        int affectRow = hnist2FollowMapper.delectByFanHnist2IDAndHnist2ID(follower_hnist2_id, hnist2_id);
        if (affectRow < 1) {
            throw new HAException(HAError.HNIST2_FOLLOW_CANCELED);
        }
    }

    @Override
    public List<Hnist2Follow> getFollows(Integer fans_user_id) {
        return hnist2FollowMapper.getByFansHnist2ID(fans_user_id);
    }

    @Override
    public void updateLastLoginAt(Integer hnist2_id) {
        hnist2UsersMapper.updateLastLoginAt(hnist2_id);
    }
}
