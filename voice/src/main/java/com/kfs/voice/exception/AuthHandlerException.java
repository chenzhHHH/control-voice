package com.kfs.voice.exception;

import com.kfs.voice.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthHandlerException extends RuntimeException {

    private static final long serialVersionUID = 7215235050267211499L;

    private String code;
    private String msg;

    public AuthHandlerException(ResultEnum resultEnum) {
        super(resultEnum.getCode());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
}
