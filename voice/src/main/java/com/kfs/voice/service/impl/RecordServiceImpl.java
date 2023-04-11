package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.Sentence;
import com.kfs.voice.entity.Word;
import com.kfs.voice.mapper.SentenceMapper;
import com.kfs.voice.mapper.WordMapper;
import com.kfs.voice.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private SentenceMapper sentenceMapper;

    @Override
    public List<Word> getWordList() {
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();

        return wordMapper.selectList(wordQueryWrapper);
    }

    @Override
    public List<Sentence> getSentenceListByWordId(String wordId) {
        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();

        sentenceQueryWrapper.eq("word_id", wordId);

        return sentenceMapper.selectList(sentenceQueryWrapper);
    }

    @Override
    public Boolean recordVoice(String sentenceId, String wordId, MultipartFile voiceFile) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("temp.wav");
            fos.write(voiceFile.getBytes()); // 写入文件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
