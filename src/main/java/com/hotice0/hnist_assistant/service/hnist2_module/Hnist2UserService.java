package com.hotice0.hnist_assistant.service.hnist2_module;

import com.hotice0.hnist_assistant.db.model.Hnist2Follow;
import com.hotice0.hnist_assistant.db.model.Hnist2FollowGoods;
import com.hotice0.hnist_assistant.exception.HAException;
import com.hotice0.hnist_assistant.db.model.Hnist2User;

import java.util.List;

/**
 * @Author HotIce0
 * @Create 2019-05-19 10:35
 */
public interface Hnist2UserService {
    void bindStudentID(String name, String cardID, String gender, String studentID) throws HAException;

    Hnist2User getHnist2UserByUUID(Integer uuid) throws HAException;

    void follow(Integer follower_hnist2_id, Integer hnist2_id) throws HAException;

    void cancelFollow(Integer follower_hnist2_id, Integer hnist2_id) throws HAException;

    List<Hnist2Follow> getFollows(Integer fans_user_id);

    void updateLastLoginAt(Integer hnist2_id);
}
