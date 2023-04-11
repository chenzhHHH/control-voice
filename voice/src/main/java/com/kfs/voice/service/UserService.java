package com.kfs.voice.service;

import com.kfs.voice.entity.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> login(User user);
}
