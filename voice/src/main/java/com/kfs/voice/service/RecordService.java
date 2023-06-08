package com.kfs.voice.service;

import com.kfs.voice.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {

    List<WordVo> getWordList(String userId, String filterType);

    WordNumVo getWordNum(String userId);

    List<SentenceVo> getSentenceList(String userId, String wordId, String filterType);

    SentenceNumVo getSentenceNum(String userId, String wordId);

    Boolean recordVoice(String recordId, String userId, String sentenceId, String wordId, MultipartFile voiceFile);

    String getVoice(String userId, String sentenceId) throws IOException;

    Boolean editSentence(String sentenceId, String sentenceText);

    List<CheckWordVo> getCheckWordList(String userId, String filterType);

    CheckWordNumVo getCheckWordNum(String userId);

    List<CheckSentenceVo> getCheckSentenceList(String userId, String wordId, String filterType);

    CheckSentenceNumVo getCheckSentenceNum(String userId, String wordId);

    Boolean checkRecord(CheckRecordVo checkRecordVo);
}
