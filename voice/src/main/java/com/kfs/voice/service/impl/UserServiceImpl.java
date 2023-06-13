package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.User;
import com.kfs.voice.entity.UserRelChecker;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.exception.CommonHandlerException;
import com.kfs.voice.mapper.UserMapper;
import com.kfs.voice.mapper.UserRelCheckerMapper;
import com.kfs.voice.service.UserService;
import com.kfs.voice.utils.JwtUtil;
import com.kfs.voice.vo.LoginUserVo;
import com.kfs.voice.vo.UserAuthVo;
import com.kfs.voice.vo.UserRelCheckerVo;
import com.kfs.voice.vo.UserVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRelCheckerMapper userRelCheckerMapper;

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

    @Override
    public List<UserVo> getUserList() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne("type", "test");
        userQueryWrapper.eq("is_delete", false);

        List<User> users = userMapper.selectList(userQueryWrapper);

        return buildUserVos(users);
    }

    @Override
    public List<UserRelCheckerVo> getUserRelCheckerList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("u.type", "test");
        queryWrapper.eq("u.is_delete", false);

        return userRelCheckerMapper.getUserRelCheckerList(queryWrapper);
    }

    @Override
    public Boolean insertOrUpdateUserRelChecker(String id, String userId, String checkerId) {
        if (Strings.isBlank(userId) || Strings.isBlank(checkerId)) {
            return false;
        }

        QueryWrapper<UserRelChecker> userRelCheckerQueryWrapper = new QueryWrapper<>();
        userRelCheckerQueryWrapper.eq("id", id);
        UserRelChecker selectUserRelChecker = userRelCheckerMapper.selectOne(userRelCheckerQueryWrapper);

        if (selectUserRelChecker == null) {
            UserRelChecker userRelChecker = new UserRelChecker();
            userRelChecker.setUserId(userId);
            userRelChecker.setCheckerId(checkerId);

            int insert = userRelCheckerMapper.insert(userRelChecker);
        } else {
            selectUserRelChecker.setCheckerId(checkerId);

            int update = userRelCheckerMapper.updateById(selectUserRelChecker);
        }

        return true;
    }

    @Override
    public List<UserRelCheckerVo> getUserRelCheckerListByCheckerId(String checkerId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("u.type", "test");
        queryWrapper.eq("u.is_delete", false);
        queryWrapper.eq("temp_rel.checkerId", checkerId);

        List<UserRelCheckerVo> userRelCheckerList = userRelCheckerMapper.getUserRelCheckerListByCheckerId(queryWrapper);

        return userRelCheckerList;
    }

    @Override
    public UserAuthVo getUserAuth(String userId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", userId);

        UserAuthVo userAuthVo = new UserAuthVo();

        User user = userMapper.selectOne(userQueryWrapper);

        if (user == null) {
            return userAuthVo;
        }

        userAuthVo.setUserId(userId);
        userAuthVo.setUsername(user.getUsername());
        userAuthVo.setCnName(user.getCnName());
        userAuthVo.setIsCheck(user.getType().contains("check"));
        userAuthVo.setIsEdit(user.getType().contains("edit"));

        return userAuthVo;
    }

    private List<UserVo> buildUserVos(List<User> users) {
        List<UserVo> userVos = new ArrayList<>();

        users.forEach(user -> {
            UserVo userVo = new UserVo();

            userVo.setId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setCnName(user.getCnName());

            userVos.add(userVo);
        });

        return userVos;
    }
}
