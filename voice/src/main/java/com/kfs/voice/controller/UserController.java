package com.kfs.voice.controller;

import com.kfs.voice.entity.User;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.UserServiceImpl;
import com.kfs.voice.vo.LoginUserVo;
import com.kfs.voice.vo.Result;
import com.kfs.voice.vo.UserRelCheckerVo;
import com.kfs.voice.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse repsponse) {
        Result<LoginUserVo> result = new Result<>();

        LoginUserVo loginUserVo = userService.login(user);

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(loginUserVo);

        return result;
    }

    @CrossOrigin
    @PostMapping("/getUserList")
    public Result getUserList() {
        Result<List<UserVo>> result = new Result<>();

        List<UserVo> userVos = userService.getUserList();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(userVos);

        return result;
    }

    @CrossOrigin
    @PostMapping("/getUserRelCheckerList")
    public Result getUserRelCheckerList() {
        Result<List<UserRelCheckerVo>> result = new Result<>();

        List<UserRelCheckerVo> userVos = userService.getUserRelCheckerList();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(userVos);

        return result;
    }

    @CrossOrigin
    @PostMapping("/insertOrUpdateUserRelChecker")
    public Result insertOrUpdateUserRelChecker(@RequestParam("id") String id, @RequestParam("userId") String userId, @RequestParam("checkerId") String checkerId) {
        Result<String> result = new Result<>();

        userService.insertOrUpdateUserRelChecker(id, userId, checkerId);

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("编辑成功");

        return result;
    }

    @CrossOrigin
    @PostMapping("/getUserRelCheckerListByCheckerId")
    public Result getUserRelCheckerListByCheckerId(@RequestParam("checkerId") String checkerId) {
        Result<List<UserRelCheckerVo>> result = new Result<>();

        List<UserRelCheckerVo> userVos = userService.getUserRelCheckerListByCheckerId(checkerId);

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(userVos);

        return result;
    }
}
