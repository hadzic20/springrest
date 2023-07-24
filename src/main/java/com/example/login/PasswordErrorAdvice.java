package com.example.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PasswordErrorAdvice {
    @ResponseBody
    @ExceptionHandler(PasswordErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String PasswordErrorHandler(PasswordErrorException ex) {
        return ex.getMessage();
    }
}
