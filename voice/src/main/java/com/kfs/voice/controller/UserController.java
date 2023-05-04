package com.kfs.voice.controller;

import com.kfs.voice.entity.User;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.UserServiceImpl;
import com.kfs.voice.vo.Result;
import com.kfs.voice.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
}
