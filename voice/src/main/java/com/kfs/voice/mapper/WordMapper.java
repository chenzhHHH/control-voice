package com.kfs.voice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kfs.voice.entity.Word;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WordMapper extends BaseMapper<Word> {
}
