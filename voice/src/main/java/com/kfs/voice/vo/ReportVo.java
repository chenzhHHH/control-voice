package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportVo implements Serializable {

    private static final long serialVersionUID = 6455611605186973125L;

    private String category;
    private String ent;
    private double totalScore;
    private double accuracyScore;
    private double accuracyScoreValue;
    private double standardScore;
    private double standardScoreValue;
    private double fluencyScore;
    private double fluencyScoreValue;
}
