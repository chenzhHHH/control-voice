package com.kfs.voice.enums;

public enum ResultEnum {

    SUCCESS("2000", "成功"),

    UNAUTHORIZED("4001", "无权限访问"),
    UNAUTHORIZED_TOKEN_NOT_MATCH("4011", "没有合法令牌"),
    UNAUTHORIZED_TOKEN_EXPIRE("4021", "令牌已过期"),
    UNAUTHORIZED_TOKEN_VERIFY_FAILURE("4031", "令牌校验失败"),
    FORBIDDEN("4003", "拒绝访问"),
    NOT_FOUND("4004", "未找到该资源"),

    INTERNAL_SERVER_ERROR("5000", "服务器内部错误"),
    SERVER_BUSY("5003", "服务器正忙，请稍后再试");

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
