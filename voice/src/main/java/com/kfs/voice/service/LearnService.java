package com.kfs.voice.service;

import com.kfs.voice.entity.Exam;
import com.kfs.voice.vo.ExamVo;
import com.kfs.voice.vo.ReportVo;
import org.springframework.web.multipart.MultipartFile;

public interface LearnService {

    Exam findExamByExamGroupIdAndText(String examGroupId, String examQuestionId);

    boolean saveExam(Exam exam);

    String transferToVoice(String content) throws Exception;

    void evalVoice(String examGroupId, String examQuestionId, String category, MultipartFile voiceFile);

    ReportVo getReport(String examGroupId);

    ExamVo getExamQuestion(String category);
}
