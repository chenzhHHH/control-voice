package com.kfs.voice.handler;

import com.kfs.voice.exception.AuthHandlerException;
import com.kfs.voice.exception.CommonHandlerException;
import com.kfs.voice.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthHandlerException.class)
    @ResponseBody
    public Result authHandlerExceptionHandler(HttpServletRequest req, AuthHandlerException e) {
        return Result.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(value = CommonHandlerException.class)
    @ResponseBody
    public Result commonHandlerExceptionHandler(HttpServletRequest req, CommonHandlerException e) {
        return Result.error(e.getCode(), e.getMsg());
    }
}
