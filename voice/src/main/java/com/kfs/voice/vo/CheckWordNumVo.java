package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckWordNumVo implements Serializable {

    private static final long serialVersionUID = 7996923551495784724L;

    private int totalNum;
    private int unFinishCheckNum;
    private int finishCheckNum;
}
