package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SentenceNumVo implements Serializable {

    private static final long serialVersionUID = 3922571446457865731L;

    private int totalNum;
    private int unFinishNum;
    private int finishNum;
}
