package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "word")
public class Word implements Serializable {
    private static final long serialVersionUID = -5585148520194943295L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String word;
    private Boolean isDelete;
}
