package com.kfs.voice.service;

import com.kfs.voice.entity.Sentence;
import com.kfs.voice.entity.Word;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecordService {

    public List<Word> getWordList();

    List<Sentence> getSentenceListByWordId(String wordId);

    Boolean recordVoice(String sentenceId, String wordId, MultipartFile voiceFile);
}
