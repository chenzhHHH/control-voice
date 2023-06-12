package com.kfs.voice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfs.voice.entity.Record;
import com.kfs.voice.entity.Sentence;
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
        QueryWrapper<Word> wrapper = new QueryWrapper<>();

        if ("unFinish".equals(filterType)) {
            wrapper.isNull("temp_word_record.finishNum")
                    .or()
                    .apply("temp_word_record.finishNum != temp_sentence.totalNum");
        } else if ("finish".equals(filterType)) {
            wrapper.apply("temp_word_record.finishNum = temp_sentence.totalNum");
        }

        return wordMapper.getWordList(userId, wrapper);
    }

    @Override
    public WordNumVo getWordNum(String userId) {

        return wordMapper.getWordNum(userId);
    }

    @Override
    public List<SentenceVo> getSentenceList(String userId, String wordId, String filterType) {
        QueryWrapper<Sentence> wrapper = new QueryWrapper<>();

        wrapper.eq("s.word_id", wordId);

        if ("unFinish".equals(filterType)) {
            wrapper.and(wp -> wp.isNull("r.id").or().eq("r.pass", "failure"));
        } else if ("finish".equals(filterType)) {
            wrapper.isNotNull("r.id");
            wrapper.and(wp -> wp.ne("r.pass", "failure").or().isNull("r.pass"));
        }

        return sentenceMapper.getSentenceList(userId, wrapper);
    }

    @Override
    public SentenceNumVo getSentenceNum(String userId, String wordId) {

        return sentenceMapper.getSentenceNum(userId, wordId);
    }

    @Override
    public Boolean recordVoice(String recordId, String userId, String sentenceId, String wordId, MultipartFile voiceFile) {
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\".<>\\|]");

        userId = pattern.matcher(userId).replaceAll("");

        sentenceId = pattern.matcher(sentenceId).replaceAll("");

        wordId = pattern.matcher(wordId).replaceAll("");

        String voiceName = sentenceId + "_" + userId + ".wav";
        String voiceDirPath = voicePath + wordId + "\\" + sentenceId + "\\";
        String voiceFilePath = voiceDirPath + voiceName;

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("id", recordId);
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
            voiceFilePath = selectRecord.getVoiceFilePath();

            if (!userId.equals(selectRecord.getUserId())) {
                selectRecord.setRemark("reread_by: " + userId + ", date: " + new Date().toString());
            } else {
                selectRecord.setPass("");
            }

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
    public String getVoice(String recordId) throws IOException {

        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("id", recordId);
        Record record = recordMapper.selectOne(recordQueryWrapper);

        if (record == null) {
            return Strings.EMPTY;
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
    public List<CheckSentenceVo> getCheckSentenceList(String userId, String wordId, String filterType) {
        QueryWrapper<Sentence> wrapper = new QueryWrapper<>();

        wrapper.eq("s.word_id", wordId);

        if ("unFinish".equals(filterType)) {
            wrapper.and(wp -> wp.isNull("r.pass").or().eq("r.pass", ""));
        } else if ("finish".equals(filterType)) {
            wrapper.and(wp -> wp.isNotNull("r.pass").ne("r.pass", ""));
        }

        return sentenceMapper.getCheckSentenceList(userId, wrapper);
    }

    @Override
    public CheckSentenceNumVo getCheckSentenceNum(String userId, String wordId) {

        return sentenceMapper.getCheckSentenceNum(userId, wordId);
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

        int update = recordMapper.updateById(record);

        return true;
    }

    @Override
    public Boolean checkRecords(CheckRecordVo checkRecordVo) {
        checkRecordVo.getRecordIds().stream().forEach(recordId -> {
            Record record = recordMapper.selectById(recordId);

            if (record == null) {
                return;
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

            int update = recordMapper.updateById(record);
        });

        return true;
    }
}
