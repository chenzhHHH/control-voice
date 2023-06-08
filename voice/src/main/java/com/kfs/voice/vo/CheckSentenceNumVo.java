package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckSentenceNumVo implements Serializable {

    private static final long serialVersionUID = -889558875999813677L;

    private int totalNum;
    private int unFinishNum;
    private int finishNum;
}
