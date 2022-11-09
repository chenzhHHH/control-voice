package com.kfs.voice.controller;

import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/testIndex")
    public Result<String> testIndex() {
        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("testIndex");

        return result;
    }

}
