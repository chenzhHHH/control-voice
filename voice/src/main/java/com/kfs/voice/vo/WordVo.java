package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WordVo implements Serializable {

    private static final long serialVersionUID = 2793396861170584901L;

    private String id;

    private String word;
    private int finishNum;
    private int totalNum;
    private Boolean isFinish;
}
