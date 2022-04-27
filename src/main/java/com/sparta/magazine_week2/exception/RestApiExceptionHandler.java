package com.sparta.magazine_week2.exception;

import com.sparta.magazine_week2.dto.response.Fail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {ErrorCustomException.class,})
    public ResponseEntity<Fail> customErrorException(ErrorCustomException ex) {
        Fail apiException = new Fail(ex.getErrorCode());
        log.error("에러발생 :" + ex.getErrorCode());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Fail> handleApiRequestErrorException(MethodArgumentNotValidException ex) {
        Fail restApiException = new Fail(ex + " " + ex.getLocalizedMessage() );
        log.error(ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

}
