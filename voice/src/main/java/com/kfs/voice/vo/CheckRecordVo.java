package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckRecordVo implements Serializable {

    private static final long serialVersionUID = 3051130270903953907L;

    private String recordId;
    private List<String> recordIds;
    private String checkUserId;
    private String checkType;
}
