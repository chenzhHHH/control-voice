package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckWordVo implements Serializable {

    private static final long serialVersionUID = -6201295181711878776L;

    private String id;

    private String word;
    private int finishCheckNum;
    private int totalNum;
}
