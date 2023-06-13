package com.kfs.voice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthVo implements Serializable {

    private static final long serialVersionUID = -1564432534565740171L;

    private String userId;
    private String username;
    private String cnName;

    private Boolean isCheck;
    private Boolean isEdit;
}
