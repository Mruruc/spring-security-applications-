package com.mruruc.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> badCredentialExceptionHandler(BadCredentialsException exception){
        return new ResponseEntity<>(Map.of("message",exception.getMessage()),HttpStatus.UNAUTHORIZED);
    }
}
