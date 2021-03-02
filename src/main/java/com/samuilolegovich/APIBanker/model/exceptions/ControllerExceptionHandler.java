package com.samuilolegovich.APIBanker.model.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import  org.springframework.http.HttpStatus ;
import  org.springframework.http.ResponseEntity ;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import  org.springframework.web.bind.annotation.ControllerAdvice ;
import  org.springframework.web.bind.annotation.ExceptionHandler ;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;



@ControllerAdvice
public class ControllerExceptionHandler extends ResponseStatusExceptionResolver {

    @ExceptionHandler(ClientNotFoundException.class)
    protected ResponseEntity<ErrorCodeAndDescription> handleNotFoundException() {
        return new ResponseEntity<>(new ErrorCodeAndDescription("404",
                "Client not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DestNotFoundException.class)
    protected ResponseEntity<ErrorCodeAndDescription> handleNotFoundExceptionDest() {
        return new ResponseEntity<>(new ErrorCodeAndDescription("404",
                "Recipient not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SourceNotFoundException.class)
    protected ResponseEntity<ErrorCodeAndDescription> handleNotFoundExceptionSource() {
        return new ResponseEntity<>(new ErrorCodeAndDescription("404",
                "Payer not found"), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMessageNotWritable() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("500",
                    "Message Not Writable"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMethodNotSupported() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("405",
                    "Method Not Supported"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerConversionNotSupported() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("500",
                    "Conversion Not Supported"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMethodArgumentNotValid() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Method ArgumentNot ValidException"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerTypeMismatchException() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Type Mismatch"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMissingServletRequestPart() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Missing Servlet Request Part"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMissingServletRequestParameter() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Missing Servlet Request Parameter"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMessageNotReadable() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Message Not Readable"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMediaTypeNotSupported() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("415",
                    "Media Type Not Supported"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerMediaTypeNotAcceptable() {
            return new ResponseEntity<>(new ErrorCodeAndDescription("406",
                    "Media Type Not Acceptable"), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorCodeAndDescription> handlerJsonProcessingException(JsonProcessingException exception) {
        if (exception instanceof InvalidFormatException) {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Invalid format"), HttpStatus.BAD_REQUEST);
        } else if (exception instanceof MismatchedInputException) {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Mismatched Input"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ErrorCodeAndDescription("400",
                    "Validation error"), HttpStatus.BAD_REQUEST);
        }
    }
}
