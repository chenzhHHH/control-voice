package com.kfs.voice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kfs.voice.entity.Word;
import com.kfs.voice.vo.CheckWordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WordMapper extends BaseMapper<Word> {

    @Select("SELECT w.id, w.word, temp_word_record.user_id AS userId, temp_word_record.finishCheckNum, temp_sentence.totalNum FROM word w " +
            "LEFT JOIN (" +
            "SELECT t_w.id, r.user_id, COUNT(*) AS finishCheckNum " +
            "FROM word t_w JOIN record r ON t_w.id = r.word_id " +
            "WHERE r.review_user_id IS NOT NULL AND r.user_id = #{userId} GROUP BY t_w.id, r.user_id" +
            ") temp_word_record ON w.id = temp_word_record.id " +
            "JOIN (" +
            "SELECT t_s.word_id, count(*) AS totalNum FROM sentence t_s GROUP BY t_s.word_id " +
            ") temp_sentence ON w.id = temp_sentence.word_id " +
            "WHERE temp_word_record.finishCheckNum IS NULL OR temp_word_record.finishCheckNum != temp_sentence.totalNum"
    )
    List<CheckWordVo> getCheckWordList(@Param("userId") String userId);
}
