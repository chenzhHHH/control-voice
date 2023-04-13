package com.kfs.voice.service;

import com.kfs.voice.entity.Word;
import com.kfs.voice.vo.SentenceVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RecordService {

    public List<Word> getWordList();

    List<SentenceVo> getSentenceList(String userId, String wordId);

    Boolean recordVoice(String userId, String sentenceId, String wordId, MultipartFile voiceFile);

    String getVoice(String userId, String sentenceId) throws IOException;
}
