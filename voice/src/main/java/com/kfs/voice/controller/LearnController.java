package com.kfs.voice.controller;

import com.kfs.voice.entity.Exam;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.LearnServiceImpl;
import com.kfs.voice.vo.ExamVo;
import com.kfs.voice.vo.ReportVo;
import com.kfs.voice.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

@RestController
@RequestMapping("/learn")
public class LearnController {

    @Autowired
    LearnServiceImpl learnService;

    @CrossOrigin
    @PostMapping("/getExamQuestion")
    public Result getExamQuestion(@RequestParam("category") String category) {
        Result<ExamVo> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());

        ExamVo examVo = learnService.getExamQuestion(category);
        result.setData(examVo);
        return result;
    }

    @CrossOrigin
    @PostMapping("/transferToVoice")
    public Result transferToVoice(@RequestParam("content") String content) throws Exception {
        String voiceByteList = learnService.transferToVoice(content);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(voiceByteList);

        return result;
    }

    @CrossOrigin
    @PostMapping("/evalVoice")
    public Result evalVoice(@RequestParam("examGroupId") String examGroupId, @RequestParam("examQuestionId") String examQuestionId, @RequestParam("category") String category, @RequestPart("voiceFile") MultipartFile voiceFile) {
        System.out.println("--------------: " + examQuestionId);

        learnService.evalVoice(examGroupId, examQuestionId, category, voiceFile);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("语音上传成功，等待测评结果");

        return result;
    }

    @CrossOrigin
    @PostMapping("/getScore")
    public Result getScore(@RequestParam("examGroupId") String examGroupId, @RequestParam("examQuestionId") String examQuestionId) {
        Exam exam = learnService.findExamByExamGroupIdAndText(examGroupId, examQuestionId);

        BigDecimal totalScore = Optional.ofNullable(exam).map(Exam::getTotalScore).orElse(BigDecimal.valueOf(-1));
        DecimalFormat df = new DecimalFormat("#.##");
        String totalScoreStr = df.format(totalScore);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(totalScoreStr);

        return result;
    }

    @CrossOrigin
    @PostMapping("/getReport")
    public Result getReport(@RequestParam("examGroupId") String examGroupId) {
        ReportVo reportVo = learnService.getReport(examGroupId);

        Result<ReportVo> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(reportVo);

        return result;
    }
}
