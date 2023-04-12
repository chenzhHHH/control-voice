package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "record")
public class Record implements Serializable {

    private static final long serialVersionUID = 9053070607626652178L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;
    private String wordId;
    private String sentenceId;
    private String voiceName;
    private String voiceFilePath;
    private Date createTime;
    private Date updateTime;
    private String reviewUserId;
    private Date reviewTime;
}
