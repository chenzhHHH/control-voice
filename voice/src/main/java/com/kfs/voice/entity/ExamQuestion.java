package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName(value = "exam_question")
public class ExamQuestion implements Serializable {
    private static final long serialVersionUID = 4422604266553243483L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String content;
    private String contentRef;
    private String category;
    private String ent;
    private boolean isDelete;

    public ExamQuestion(String content, String contentRef, String category, String ent, boolean isDelete) {
        this.content = content;
        this.contentRef = contentRef;
        this.category = category;
        this.ent = ent;
        this.isDelete = isDelete;
    }
}