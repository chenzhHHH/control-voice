package com.kfs.voice.service;

import com.kfs.voice.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecordService {

    List<WordVo> getWordList(String userId, String filterType);

    List<SentenceVo> getSentenceList(String userId, String wordId, String filterType);

    Boolean recordVoice(String userId, String sentenceId, String wordId, MultipartFile voiceFile);

    String getVoice(String userId, String sentenceId) throws IOException;

    SentenceNumVo getSentenceNum(String userId, String wordId);

    WordNumVo getWordNum(String userId);

    Boolean editSentence(String sentenceId, String sentenceText);

    List<CheckWordVo> getCheckWordList(String userId, String filterType);
}
