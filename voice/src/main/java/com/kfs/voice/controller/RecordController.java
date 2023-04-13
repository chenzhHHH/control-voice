package com.kfs.voice.controller;

import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.RecordServiceImpl;
import com.kfs.voice.vo.Result;
import com.kfs.voice.vo.SentenceNumVo;
import com.kfs.voice.vo.SentenceVo;
import com.kfs.voice.vo.WordVo;
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
    public Result getWordList(@RequestParam("userId") String userId) {
        Result<List<WordVo>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getWordList(userId));

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
    public Result recordVoice(@RequestParam("userId") String userId, @RequestParam("sentenceId") String sentenceId, @RequestParam("wordId") String wordId, @RequestPart("voiceFile") MultipartFile voiceFile) {
        Boolean isRecord = recordService.recordVoice(userId, sentenceId, wordId, voiceFile);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("语音上传成功");

        return result;
    }

    @CrossOrigin
    @PostMapping("/getVoice")
    public Result getVoice(@RequestParam("userId") String userId, @RequestParam("sentenceId") String sentenceId) throws IOException {
        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getVoice(userId, sentenceId));

        return result;
    }
}
