package com.kfs.voice.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VoiceVo {
    private String text;
    private MultipartFile voiceFile;
}
