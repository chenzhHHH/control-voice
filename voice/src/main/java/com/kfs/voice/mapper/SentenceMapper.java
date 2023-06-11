package com.kfs.voice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kfs.voice.entity.Sentence;
import com.kfs.voice.vo.CheckSentenceNumVo;
import com.kfs.voice.vo.CheckSentenceVo;
import com.kfs.voice.vo.SentenceNumVo;
import com.kfs.voice.vo.SentenceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    @Select("SELECT s.id, r.id AS recordId, s.word_id, s.sentence, r.pass, " +
            "(r.id IS NOT NULL) AS isRecord, " +
            "(SELECT find_in_set('edit', temp_user.type) FROM user temp_user WHERE temp_user.id = #{userId}) AS isEdit " +
            "FROM sentence s " +
            "LEFT JOIN record r ON s.id = r.sentence_id AND r.user_id = #{userId} " +
            "${ew.customSqlSegment}"
    )
    List<SentenceVo> getSentenceList(@Param("userId") String userId, @Param(Constants.WRAPPER) QueryWrapper wrapper);

    @Select("SELECT (SELECT COUNT(*) FROM sentence temp_sentence WHERE temp_sentence.word_id = #{wordId}) AS totalNum, ((SELECT COUNT(*) FROM sentence temp_sentence WHERE temp_sentence.word_id = #{wordId}) - COUNT(*)) AS unFinishNum, COUNT(*) AS finishNum " +
            "FROM sentence s " +
            "LEFT JOIN record r ON s.id = r.sentence_id AND r.user_id = #{userId} " +
            "WHERE s.word_id = #{wordId} AND r.id IS NOT NULL AND (r.pass != 'failure' OR r.pass IS NULL)"
    )
    SentenceNumVo getSentenceNum(@Param("userId") String userId, @Param("wordId") String wordId);

    @Select("SELECT " +
            "s.id, r.id AS recordId, s.word_id, s.sentence, r.pass, " +
            "(r.id IS NOT NULL) AS isRecord, " +
            "(r.pass != '' AND r.pass IS NOT NULL) AS isCheck, " +
            "(SELECT find_in_set('edit', temp_user.type) FROM user temp_user WHERE temp_user.id = #{userId}) AS isEdit " +
            "FROM sentence s " +
            "LEFT JOIN record r ON s.id = r.sentence_id AND r.user_id = #{userId} " +
            "${ew.customSqlSegment}"
    )
    List<CheckSentenceVo> getCheckSentenceList(@Param("userId") String userId, @Param(Constants.WRAPPER) QueryWrapper wrapper);

    @Select("SELECT " +
            "(SELECT COUNT(*) FROM sentence temp_sentence WHERE temp_sentence.word_id = #{wordId}) AS totalNum, " +
            "((SELECT COUNT(*) FROM sentence temp_sentence WHERE temp_sentence.word_id = #{wordId}) - COUNT(*)) AS unFinishNum, " +
            "COUNT(*) AS finishNum " +
            "FROM sentence s " +
            "LEFT JOIN record r ON s.id = r.sentence_id AND r.user_id = #{userId} " +
            "WHERE s.word_id = #{wordId} AND (r.pass != '' AND r.pass IS NOT NULL)"
    )
    CheckSentenceNumVo getCheckSentenceNum(@Param("userId") String userId, @Param("wordId") String wordId);
}
