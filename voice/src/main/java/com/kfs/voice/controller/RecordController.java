package com.kfs.voice.controller;

import com.kfs.voice.entity.Sentence;
import com.kfs.voice.entity.Word;
import com.kfs.voice.enums.ResultEnum;
import com.kfs.voice.service.impl.RecordServiceImpl;
import com.kfs.voice.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;

    @CrossOrigin
    @PostMapping("/getWordList")
    public Result getWordList(){
        Result<List<Word>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getWordList());

        return result;
    }

    @CrossOrigin
    @PostMapping("/getSentenceListByWordId")
    public Result getSentenceListByWordId(@RequestParam("wordId") String wordId){
        Result<List<Sentence>> result = new Result<>();

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(recordService.getSentenceListByWordId(wordId));

        return result;
    }

    @CrossOrigin
    @PostMapping("/recordVoice")
    public Result recordVoice(@RequestParam("sentenceId") String sentenceId, @RequestParam("wordId") String wordId, @RequestPart("voiceFile") MultipartFile voiceFile) {
        Boolean isRecord = recordService.recordVoice(sentenceId, wordId, voiceFile);

        Result<String> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData("语音上传成功");

        return result;
    }
}
