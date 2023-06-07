package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SentenceVo implements Serializable {

    private static final long serialVersionUID = -1100614119576934771L;

    private String id;

    private String recordId;
    private String wordId;
    private String sentence;
    private Boolean isRecord;
    private Boolean isEdit;
}
