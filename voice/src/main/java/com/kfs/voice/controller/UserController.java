package com.kfs.voice.controller;

import com.kfs.voice.entity.User;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.UserServiceImpl;
import com.kfs.voice.utils.JwtUtil;
import com.kfs.voice.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse repsponse) {
        Result<User> result = new Result<>();

        Map<String, Object> userMap = userService.login(user);

        repsponse.setHeader(JwtUtil.USER_LOGIN_TOKEN, (String) userMap.get("token"));

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData((User) userMap.get("user"));

        return result;
    }
}
