package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sentence")
public class Sentence implements Serializable {
    private static final long serialVersionUID = -6785866010707341992L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String wordId;
    private String sentence;
    private Boolean isDelete;
}
