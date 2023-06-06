package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRelCheckerVo implements Serializable {

    private static final long serialVersionUID = 1290623914857319681L;

    private String id;
    private String userId;
    private String username;
    private String checkerId;
    private String checkername;

    private int finishNum;
    private int totalNum;
}
