package com.samuilolegovich.APIBanker.model.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import  org.springframework.http.HttpStatus ;
import  org.springframework.http.ResponseEntity ;
import  org.springframework.web.bind.annotation.ControllerAdvice ;
import  org.springframework.web.bind.annotation.ExceptionHandler ;
import  org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler ;


@ControllerAdvice
public class ExceptionHandlerFromRest extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorCodeAndDescription> handleNotFoundException() {
        return new ResponseEntity<>(new ErrorCodeAndDescription("404",
                "There is no such user"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorCodeAndDescription> handleDataIntegrityViolationException() {
        return new ResponseEntity<>(new ErrorCodeAndDescription("500",
                "Input data validation error"), HttpStatus.BAD_REQUEST);
    }



}
