package org.example.day7springboot.controller;

import org.example.day7springboot.service.NotAmongLegalAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHander {


  @ExceptionHandler(NotAmongLegalAgeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handNotAmongLegalAgeException(Exception e){
    return e.getMessage();
  }

}
