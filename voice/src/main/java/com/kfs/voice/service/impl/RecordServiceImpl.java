package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.Record;
import com.kfs.voice.entity.Sentence;
import com.kfs.voice.entity.User;
import com.kfs.voice.entity.Word;
import com.kfs.voice.mapper.RecordMapper;
import com.kfs.voice.mapper.SentenceMapper;
import com.kfs.voice.mapper.UserMapper;
import com.kfs.voice.mapper.WordMapper;
import com.kfs.voice.service.RecordService;
import com.kfs.voice.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private UserMapper userMapper;

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
    public List<WordVo> getWordList(String userId, String filterType) {
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

            if ("unFinish".equals(filterType) && wordVo.getIsFinish()) {
                return;
            } else if ("finish".equals(filterType) && !wordVo.getIsFinish()) {
                return;
            }

            wordVos.add(wordVo);
        });

        return wordVos;
    }

    @Override
    public List<SentenceVo> getSentenceList(String userId, String wordId, String filterType) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", userId);
        User user = userMapper.selectOne(userQueryWrapper);

        if (user == null) {
            return new ArrayList<>();
        }

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
            sentenceVo.setIsEdit("edit".equals(user.getType()));
            sentenceVo.setIsRecord(isRecord);

            sentenceVos.add(sentenceVo);
        });

        return sentenceVos;
    }

    @Override
    public Boolean recordVoice(String userId, String sentenceId, String wordId, MultipartFile voiceFile) {
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\".<>\\|]");

        userId = pattern.matcher(userId).replaceAll("");

        sentenceId = pattern.matcher(sentenceId).replaceAll("");

        wordId = pattern.matcher(wordId).replaceAll("");

        String voiceName = sentenceId + "_" + userId + ".wav";
        String voiceDirPath = voicePath + wordId + "\\" + sentenceId + "\\";
        String voiceFilePath = voiceDirPath + voiceName;

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
            selectRecord.setVoiceName(voiceName);
            selectRecord.setVoiceFilePath(voiceFilePath);
            selectRecord.setUpdateTime(new Date());

            int update = recordMapper.updateById(selectRecord);
        }

        saveVoiceFile(voiceFile, voiceDirPath, voiceFilePath);

        return true;
    }

    private void saveVoiceFile(MultipartFile voiceFile, String voiceDirPath, String voiceFilePath) {
        FileOutputStream fos = null;

        try {
            File dir = new File(voiceDirPath);

            if (!dir.exists()) {
                dir.mkdirs();
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

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("user_id", userId)
                .eq("sentence_id", sentenceId);
        Record record = recordMapper.selectOne(recordQueryWrapper);

        if (record == null) {
            return "";
        }

        String voiceFilePath = record.getVoiceFilePath();

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

    @Override
    public WordNumVo getWordNum(String userId) {
        WordNumVo wordNumVo = new WordNumVo();

        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.eq("is_delete", false);
        List<Word> words = wordMapper.selectList(wordQueryWrapper);


        int finishNum = 0;

        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);

            QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
            sentenceQueryWrapper.eq("word_id", word.getId());
            int totalSentenceNumByWordId = Math.toIntExact(sentenceMapper.selectCount(sentenceQueryWrapper));

            QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
            recordQueryWrapper.eq("user_id", userId)
                    .eq("word_id", word.getId());
            int finishSentenceNumByWordId = Math.toIntExact(recordMapper.selectCount(recordQueryWrapper));

            if (totalSentenceNumByWordId - finishSentenceNumByWordId == 0) {
                finishNum++;
            }
        }

        wordNumVo.setSumNum(words.size());
        wordNumVo.setFinishNum(finishNum);
        wordNumVo.setUnFinishNum(words.size() - finishNum);

        return wordNumVo;
    }

    private boolean deleteVoiceFile(String voiceFilePath) {
        File file = new File(voiceFilePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                file.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean editSentence(String sentenceId, String sentenceText) {
        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
        sentenceQueryWrapper.eq("id", sentenceId);

        Sentence sentence = sentenceMapper.selectOne(sentenceQueryWrapper);
        if (sentence == null) {
            return false;
        }

        sentence.setSentence(sentenceText);
        int updateIndex = sentenceMapper.updateById(sentence);

        if (updateIndex > 0) {
            QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
            recordQueryWrapper.eq("sentence_id", sentence.getId());
            List<Record> records = recordMapper.selectList(recordQueryWrapper);

            List<String> recordIds = records.stream().map(Record::getId).collect(Collectors.toList());
            if (!recordIds.isEmpty()) {
                int deleteIndex = recordMapper.deleteBatchIds(recordIds);
            }

            List<String> recordVoiceFilePaths = records.stream().map(Record::getVoiceFilePath).collect(Collectors.toList());
            recordVoiceFilePaths.forEach(recordVoiceFilePath -> {
                boolean isDelete = deleteVoiceFile(recordVoiceFilePath);
            });
        }

        return true;
    }

    @Override
    public List<CheckWordVo> getCheckWordList(String userId, String filterType) {
        QueryWrapper<Word> wrapper = new QueryWrapper<>();

        if ("unFinish".equals(filterType)) {
            wrapper.isNull("temp_word_record.finishCheckNum")
                    .or()
                    .apply("temp_word_record.finishCheckNum != temp_sentence.totalNum");
        } else if ("finish".equals(filterType)) {
            wrapper.apply("temp_word_record.finishCheckNum = temp_sentence.totalNum");
        }

        return wordMapper.getCheckWordList(userId, wrapper);
    }

    @Override
    public CheckWordNumVo getCheckWordNum(String userId) {

        return wordMapper.getCheckWordNum(userId);
    }

    @Override
    public Boolean checkRecord(CheckRecordVo checkRecordVo) {
        Record record = recordMapper.selectById(checkRecordVo.getRecordId());

        if (record == null) {
            return false;
        }

        String pass = Strings.EMPTY;

        if ("合格".equals(checkRecordVo.getCheckType())) {
            pass = "pass";
        } else if ("不合格".equals(checkRecordVo.getCheckType())) {
            pass = "failure";
        }

        record.setReviewUserId(checkRecordVo.getCheckUserId());
        record.setReviewTime(new Date());
        record.setPass(pass);

        return true;
    }
}
