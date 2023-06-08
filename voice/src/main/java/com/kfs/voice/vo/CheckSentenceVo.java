package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckSentenceVo implements Serializable {

    private static final long serialVersionUID = -4331717975439642063L;

    private String id;

    private String recordId;
    private String wordId;
    private String sentence;
    private String pass;
    private Boolean isRecord;
    private Boolean isCheck;
//    private Boolean isEdit;
}
