package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.User;
import com.kfs.voice.mapper.UserMapper;
import com.kfs.voice.service.UserService;
import com.kfs.voice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> login(User user) {
        if ((user.getUsername() == null || user.getUsername().equals("")) || (user.getPhone() == null || user.getPhone().equals(""))) {
            throw new RuntimeException("用户名手机不能为空");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername());
        userQueryWrapper.eq("phone", user.getPhone());

        User selectUser = userMapper.selectOne(userQueryWrapper);
        if (selectUser == null) {
            throw new RuntimeException("用户不存在");
        }

        String token = JwtUtil.createToken(selectUser);

        return new HashMap<>() {
            {
                put("user", selectUser);
                put("token", token);
            }
        };
    }
}
