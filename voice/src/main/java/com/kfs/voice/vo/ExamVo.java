package com.kfs.voice.vo;

import com.kfs.voice.entity.ExamQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExamVo {
    private String examGroupId;
    private List<ExamQuestion> examQuestionList = new ArrayList<>();
}
