package com.kfs.voice.controller;

import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.RecordServiceImpl;
import com.kfs.voice.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;

    @CrossOrigin
    @PostMapping("/getWordList")
    public Result getWordList(@RequestParam("userId") String userId, @RequestParam("filterType") String filterType) {
        Result<List<WordVo>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getWordList(userId, filterType));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getWordNum")
    public Result getWordNum(@RequestParam("userId") String userId) {
        Result<WordNumVo> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getWordNum(userId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getSentenceListByWordId")
    public Result getSentenceListByWordId(@RequestParam("userId") String userId, @RequestParam("wordId") String wordId, @RequestParam("filterType") String filterType) {
        Result<List<SentenceVo>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getSentenceList(userId, wordId, filterType));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getSentenceNumByWordId")
    public Result getSentenceNumByWordId(@RequestParam("userId") String userId, @RequestParam("wordId") String wordId) {
        Result<SentenceNumVo> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getSentenceNum(userId, wordId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/recordVoice")
    public Result recordVoice(@RequestParam("recordId") String recordId, @RequestParam("userId") String userId, @RequestParam("sentenceId") String sentenceId, @RequestParam("wordId") String wordId, @RequestPart("voiceFile") MultipartFile voiceFile) {
        Boolean isRecord = recordService.recordVoice(recordId, userId, sentenceId, wordId, voiceFile);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("语音上传成功");

        return result;
    }

    @CrossOrigin
    @PostMapping("/getVoice")
    public Result getVoice(@RequestParam("recordId") String recordId) throws IOException {
        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getVoice(recordId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/editSentence")
    public Result editSentence(@RequestParam("sentenceId") String sentenceId, @RequestParam("sentenceText") String sentenceText) throws IOException {
        Boolean isModify = recordService.editSentence(sentenceId, sentenceText);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("修改成功");

        return result;
    }

    @CrossOrigin
    @PostMapping("/getCheckWordList")
    public Result getCheckWordList(@RequestParam("userId") String userId, @RequestParam("filterType") String filterType) {
        Result<List<CheckWordVo>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getCheckWordList(userId, filterType));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getCheckWordNum")
    public Result getCheckWordNum(@RequestParam("userId") String userId) {
        Result<CheckWordNumVo> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getCheckWordNum(userId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getCheckSentenceList")
    public Result getCheckSentenceList(@RequestParam("userId") String userId, @RequestParam("wordId") String wordId, @RequestParam("filterType") String filterType) {
        Result<List<CheckSentenceVo>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getCheckSentenceList(userId, wordId, filterType));

        return result;
    }

    @CrossOrigin
    @PostMapping("/getCheckSentenceNum")
    public Result getCheckSentenceNum(@RequestParam("userId") String userId, @RequestParam("wordId") String wordId) {
        Result<CheckSentenceNumVo> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getCheckSentenceNum(userId, wordId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/checkRecord")
    public Result checkRecord(@RequestBody CheckRecordVo checkRecordVo) {
        Result<String> result = new Result<>();

        Boolean isCheck = recordService.checkRecord(checkRecordVo);

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("审核成功");

        return result;
    }
}
