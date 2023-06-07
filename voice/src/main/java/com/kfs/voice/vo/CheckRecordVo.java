package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckRecordVo implements Serializable {

    private static final long serialVersionUID = 3051130270903953907L;

    private String recordId;
    private String userId;
    private String sentenceId;
    private String wordId;
    private String checkUserId;
    private String checkType;
}
