package org.example.day7springboot.service;

public class BigAgeAndLowSalaryException extends RuntimeException {
  public BigAgeAndLowSalaryException(String message){
    super(message);
  }

}
