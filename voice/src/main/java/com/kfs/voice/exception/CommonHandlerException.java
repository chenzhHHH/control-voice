package com.kfs.voice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonHandlerException extends RuntimeException {

    private static final long serialVersionUID = -4428158954660593635L;

    private String code;
    private String msg;

    public CommonHandlerException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }
}
