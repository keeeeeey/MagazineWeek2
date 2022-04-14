package com.sparta.magazine_week2.dto;

import com.sparta.magazine_week2.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class Fail {
    private String result = "fail";
    private String msg;
    private HttpStatus status;
    private String code;

    public Fail(final ErrorCode errorCode){
        this.msg = errorCode.getErrorMessage();
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getErrorCode();
    }

    public Fail(final String msg){
        this.msg = msg;
    }
}
