package com.kfs.voice.enums;

public enum ResultEnum {

    UNKNOWN_ERROR(-1,"unKnowError"),
    SUCCESS(1,"success");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
