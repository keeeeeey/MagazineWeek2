package com.sparta.magazine_week2.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AroundHubException {
    private String msg;
    private HttpStatus httpStatus;
}
