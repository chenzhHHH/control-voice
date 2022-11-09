package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.Exam;
import com.kfs.voice.entity.ExamQuestion;
import com.kfs.voice.mapper.ExamMapper;
import com.kfs.voice.mapper.ExamQuestionMapper;
import com.kfs.voice.service.LearnService;
import com.kfs.voice.utils.XunFeiUtil;
import com.kfs.voice.vo.ExamVo;
import com.kfs.voice.vo.ReportVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class LearnServiceImpl implements LearnService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    public Exam findExamByExamGroupIdAndText(String examGroupId, String examQuestionId) {
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("exam_group_id", examGroupId);
        examQueryWrapper.eq("exam_question_id", examQuestionId);

        Exam exam = examMapper.selectOne(examQueryWrapper);
        return exam;
    }

    @Override
    public boolean saveExam(Exam exam) {
        return examMapper.insert(exam) > 0;
    }

    @Override
    public String transferToVoice(String content) throws Exception {
        XunFeiUtil xunFeiUtil = new XunFeiUtil();
        return xunFeiUtil.transferToVoice(content);
    }

    @Override
    public void evalVoice(String examGroupId, String examQuestionId, String category, MultipartFile voiceFile) {
        QueryWrapper<ExamQuestion> examQuestionQueryWrapper = new QueryWrapper<>();
        examQuestionQueryWrapper.eq("category", category);
        examQuestionQueryWrapper.eq("is_delete", false);

        List<ExamQuestion> examQuestionList = examQuestionMapper.selectList(examQuestionQueryWrapper);

        ExamQuestion examQuestion = examQuestionList.stream().filter(e -> e.getId().equals(examQuestionId)).findFirst().orElse(new ExamQuestion());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("xxx.pcm");
            fos.write(voiceFile.getBytes()); // 写入文件

            XunFeiUtil xunFeiUtil = new XunFeiUtil();
            xunFeiUtil.evalVoice(examGroupId, examQuestionId, examQuestion);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ReportVo getReport(String examGroupId) {
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("exam_group_id", examGroupId);

        List<Exam> exams = examMapper.selectList(examQueryWrapper);
        int total = exams.size();

        ReportVo reportVo = new ReportVo();

        Double totalScore = exams.stream().map(Exam::getTotalScore).reduce(BigDecimal::add).map(e -> {
            BigDecimal divide = e.divide(BigDecimal.valueOf(total), 2, ROUND_HALF_UP);
            return divide.doubleValue();
        }).orElse(0d);

        Double accuracyScore = exams.stream().map(Exam::getAccuracyScore).reduce(BigDecimal::add).map(e -> {
            BigDecimal divide = e.divide(BigDecimal.valueOf(total), 2, ROUND_HALF_UP);
            return divide.doubleValue();
        }).orElse(0d);

        double accuracyScoreValue = accuracyScore * (5.0 / 100);

        Double standardScore = exams.stream().map(Exam::getStandardScore).reduce(BigDecimal::add).map(e -> {
            BigDecimal divide = e.divide(BigDecimal.valueOf(total), 2, ROUND_HALF_UP);
            return divide.doubleValue();
        }).orElse(0d);

        double standardScoreValue = standardScore * (5.0 / 100);

        Double fluencyScore = exams.stream().map(Exam::getFluencyScore).reduce(BigDecimal::add).map(e -> {
            BigDecimal divide = e.divide(BigDecimal.valueOf(total), 2, ROUND_HALF_UP);
            return divide.doubleValue();
        }).orElse(0d);

        double fluencyScoreValue = fluencyScore * (5.0 / 100);

        reportVo.setCategory(exams.stream().map(Exam::getCategory).findFirst().orElse(Strings.EMPTY));
        reportVo.setEnt(exams.stream().map(Exam::getEnt).findFirst().orElse(Strings.EMPTY));
        reportVo.setTotalScore(totalScore);
        reportVo.setAccuracyScore(accuracyScore);
        reportVo.setAccuracyScoreValue(accuracyScoreValue);
        reportVo.setStandardScore(standardScore);
        reportVo.setStandardScoreValue(standardScoreValue);
        reportVo.setFluencyScore(fluencyScore);
        reportVo.setFluencyScoreValue(fluencyScoreValue);
        return reportVo;
    }

    @Override
    public ExamVo getExamQuestion(String category) {
        ExamVo examVo = new ExamVo();

        String examGroupId = UUID.randomUUID().toString().replaceAll("-", "");
        examVo.setExamGroupId(examGroupId);

        QueryWrapper<ExamQuestion> examQuestionQueryWrapper = new QueryWrapper<>();
        examQuestionQueryWrapper.eq("category", category);
        examQuestionQueryWrapper.eq("is_delete", false);
        examQuestionQueryWrapper.orderByAsc("content");

        examVo.setExamQuestionList(examQuestionMapper.selectList(examQuestionQueryWrapper));

        return examVo;
    }

}
