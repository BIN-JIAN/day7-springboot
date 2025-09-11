package org.example.day7springboot.controller;

import org.example.day7springboot.exception.BigAgeAndLowSalaryException;
import org.example.day7springboot.exception.DuplicateEmployeeException;
import org.example.day7springboot.exception.EmployeeStatusException;
import org.example.day7springboot.exception.NotAmongLegalAgeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(NotAmongLegalAgeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handNotAmongLegalAgeException(Exception e) {
    return e.getMessage();
  }

  @ExceptionHandler(BigAgeAndLowSalaryException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String BigAgeAndLowSalaryException(Exception e) {
    return e.getMessage();
  }

  @ExceptionHandler(DuplicateEmployeeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleDuplicateEmployeeException(Exception e) {
    return e.getMessage();
  }

  @ExceptionHandler(EmployeeStatusException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleEmployeeStatusException(Exception e) {
    return e.getMessage();
  }
}
