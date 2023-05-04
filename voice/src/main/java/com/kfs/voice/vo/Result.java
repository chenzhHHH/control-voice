package com.kfs.voice.vo;

import com.kfs.voice.enums.ResultEnum;
import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public static Result error(String code, String msg) {
        Result rb = new Result();
        rb.setCode(code);
        rb.setMsg(msg);
        rb.setData(null);
        return rb;
    }

    public static Result error(ResultEnum resultEnum) {
        Result rb = new Result();
        rb.setCode(resultEnum.getCode());
        rb.setMsg(resultEnum.getMsg());
        rb.setData(null);
        return rb;
    }
}
