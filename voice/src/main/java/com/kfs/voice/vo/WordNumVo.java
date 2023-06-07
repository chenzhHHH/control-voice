package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WordNumVo implements Serializable {

    private static final long serialVersionUID = -3217072743346326518L;

    private int totalNum;
    private int unFinishNum;
    private int finishNum;
}
