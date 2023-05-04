package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.User;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.exception.CommonHandlerException;
import com.kfs.voice.mapper.UserMapper;
import com.kfs.voice.service.UserService;
import com.kfs.voice.utils.JwtUtil;
import com.kfs.voice.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginUserVo login(User user) {
        if ((user.getUsername() == null || user.getUsername().equals("")) || (user.getPhone() == null || user.getPhone().equals(""))) {
            throw new CommonHandlerException(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), "用户名手机不能为空");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername());
        userQueryWrapper.eq("phone", user.getPhone());

        User selectUser = userMapper.selectOne(userQueryWrapper);
        if (selectUser == null) {
            throw new CommonHandlerException(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), "用户不存在");
        }

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(selectUser.getId());
        loginUserVo.setUsername(selectUser.getUsername());
        loginUserVo.setPhone(selectUser.getPhone());
        loginUserVo.setCnName(selectUser.getCnName());
        loginUserVo.setToken(JwtUtil.createToken(selectUser));

        return loginUserVo;
    }
}
