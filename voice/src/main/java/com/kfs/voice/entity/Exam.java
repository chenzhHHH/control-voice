package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName(value = "exam")
public class Exam implements Serializable {

    private static final long serialVersionUID = 2277228842862305895L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String category;
    private String ent;
    private String examGroupId;
    private String examQuestionId;
    private String content;
    private BigDecimal accuracyScore;
    private int begPos;
    private int endPos;
    private String exceptInfo;
    private Boolean isRejected;
    private String rejectType;
    private BigDecimal standardScore;
    private BigDecimal totalScore;
    private BigDecimal fluencyScore;
    private BigDecimal toneScore;
    private BigDecimal phoneScore;
    private BigDecimal integrityScore;
    private int timeLen;
    private BigDecimal emotionScore;
    private String scorePattern;
    private int wordCount;
}
