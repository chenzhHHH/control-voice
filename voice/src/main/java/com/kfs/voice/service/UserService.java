package com.kfs.voice.service;

import com.kfs.voice.entity.User;
import com.kfs.voice.vo.LoginUserVo;
import com.kfs.voice.vo.UserRelCheckerVo;
import com.kfs.voice.vo.UserVo;

import java.util.List;

public interface UserService {
    LoginUserVo login(User user);

    List<UserVo> getUserList();

    List<UserRelCheckerVo> getUserRelCheckerList();

    Boolean insertOrUpdateUserRelChecker(String id, String userId, String checkerId);

    List<UserRelCheckerVo> getUserRelCheckerListByCheckerId(String checkerId);
}
