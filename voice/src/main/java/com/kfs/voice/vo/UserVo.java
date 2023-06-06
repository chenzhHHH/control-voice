package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 4837235628798832149L;

    private String id;
    private String username;
    private String cnName;
}
