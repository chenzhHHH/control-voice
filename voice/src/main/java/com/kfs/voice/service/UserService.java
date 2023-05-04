package com.kfs.voice.service;

import com.kfs.voice.entity.User;
import com.kfs.voice.vo.LoginUserVo;

public interface UserService {
    LoginUserVo login(User user);
}
