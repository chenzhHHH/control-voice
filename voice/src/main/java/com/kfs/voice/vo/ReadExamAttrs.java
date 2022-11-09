package com.kfs.voice.vo;

import lombok.Data;

@Data
public class ReadExamAttrs {
    private double accuracyScore;
    private int begPos;
    private String content;
    private int endPos;
    private String exceptInfo;
    private Boolean isRejected;
    private String rejectType;
    private double standardScore;
    private double totalScore;
    private double fluencyScore;
    private double toneScore;
    private double phoneScore;
    private double integrityScore;
    private int timeLen;
    private double emotionScore;
    private String scorePattern;
    private int wordCount;
}
