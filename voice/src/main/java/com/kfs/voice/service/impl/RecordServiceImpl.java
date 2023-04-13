package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.Record;
import com.kfs.voice.entity.Sentence;
import com.kfs.voice.entity.Word;
import com.kfs.voice.mapper.RecordMapper;
import com.kfs.voice.mapper.SentenceMapper;
import com.kfs.voice.mapper.WordMapper;
import com.kfs.voice.service.RecordService;
import com.kfs.voice.vo.SentenceNumVo;
import com.kfs.voice.vo.SentenceVo;
import com.kfs.voice.vo.WordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private SentenceMapper sentenceMapper;

    @Autowired
    private RecordMapper recordMapper;

    private String voicePath;

    @Value("${voice.path}")
    private void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    @Override
    public List<WordVo> getWordList(String userId) {
        List<WordVo> wordVos = new ArrayList<>();

        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.eq("is_delete", false);
        List<Word> words = wordMapper.selectList(wordQueryWrapper);

        words.forEach(word -> {
            WordVo wordVo = new WordVo();

            wordVo.setId(word.getId());
            wordVo.setWord(word.getWord());

            QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
            sentenceQueryWrapper.eq("word_id", word.getId());
            int totalSentenceNumByWordId = Math.toIntExact(sentenceMapper.selectCount(sentenceQueryWrapper));

            QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
            recordQueryWrapper.eq("user_id", userId)
                    .eq("word_id", word.getId());
            int finishSentenceNumByWordId = Math.toIntExact(recordMapper.selectCount(recordQueryWrapper));

            wordVo.setUnfinishNum(totalSentenceNumByWordId - finishSentenceNumByWordId);
            wordVo.setFinishNum(finishSentenceNumByWordId);
            wordVo.setIsFinish((totalSentenceNumByWordId - finishSentenceNumByWordId) == 0);

            wordVos.add(wordVo);
        });

        return wordVos;
    }

    @Override
    public List<SentenceVo> getSentenceList(String userId, String wordId, String filterType) {
        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
        sentenceQueryWrapper.eq("word_id", wordId)
                .eq("is_delete", false);
        List<Sentence> sentences = sentenceMapper.selectList(sentenceQueryWrapper);

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("user_id", userId)
                .eq("word_id", wordId);
        List<Record> records = recordMapper.selectList(recordQueryWrapper);

        List<SentenceVo> sentenceVos = new ArrayList<>();
        sentences.forEach(sentence -> {
            boolean isRecord = records.stream().anyMatch(record -> record.getSentenceId().equals(sentence.getId()));

            if ("unFinish".equals(filterType) && isRecord) {
                return;
            } else if ("finish".equals(filterType) && !isRecord) {
                return;
            }

            SentenceVo sentenceVo = new SentenceVo();
            sentenceVo.setId(sentence.getId());
            sentenceVo.setWordId(sentence.getWordId());
            sentenceVo.setSentence(sentence.getSentence());
            sentenceVo.setIsRecord(isRecord);

            sentenceVos.add(sentenceVo);
        });

        return sentenceVos;
    }

    @Override
    public Boolean recordVoice(String userId, String sentenceId, String wordId, MultipartFile voiceFile) {
        String voiceName = sentenceId + "_" + userId + ".wav";
        String voiceFilePath = voicePath + voiceName;

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("user_id", userId)
                .eq("word_id", wordId)
                .eq("sentence_id", sentenceId);
        Record selectRecord = recordMapper.selectOne(recordQueryWrapper);

        if (selectRecord == null) {
            Record record = new Record();
            record.setUserId(userId);
            record.setWordId(wordId);
            record.setSentenceId(sentenceId);
            record.setVoiceName(voiceName);
            record.setVoiceFilePath(voiceFilePath);
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());

            int insert = recordMapper.insert(record);
        } else {
            selectRecord.setUpdateTime(new Date());
            int update = recordMapper.updateById(selectRecord);
        }

        saveVoiceFile(voiceFile, voiceFilePath);

        return true;
    }

    private void saveVoiceFile(MultipartFile voiceFile, String voiceFilePath) {
        FileOutputStream fos = null;

        try {
            File dir = new File(voicePath);

            if (!dir.exists()) {
                dir.mkdir();
            }

            fos = new FileOutputStream(voiceFilePath);
            fos.write(voiceFile.getBytes());
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

    private void checkFileExists(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    @Override
    public String getVoice(String userId, String sentenceId) throws IOException {
        String voiceName = sentenceId + "_" + userId + ".wav";
        String voiceFilePath = voicePath + voiceName;

        File file = new File(voiceFilePath);
        checkFileExists(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream bin = null;

        try {
            bin = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];

            while (bin.read(buffer) > 0) {
                bos.write(buffer);
            }

            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } finally {
            try {
                bin.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SentenceNumVo getSentenceNum(String userId, String wordId) {
        SentenceNumVo sentenceNumVo = new SentenceNumVo();

        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
        sentenceQueryWrapper.eq("word_id", wordId)
                .eq("is_delete", false);
        List<Sentence> sentences = sentenceMapper.selectList(sentenceQueryWrapper);

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("user_id", userId)
                .eq("word_id", wordId);
        List<Record> records = recordMapper.selectList(recordQueryWrapper);

        sentenceNumVo.setSumNum(sentences.size());
        sentenceNumVo.setFinishNum(records.size());
        sentenceNumVo.setUnFinishNum(sentences.size() - records.size());

        return sentenceNumVo;
    }
}
