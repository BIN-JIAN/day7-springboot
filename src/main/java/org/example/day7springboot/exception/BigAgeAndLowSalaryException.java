package org.example.day7springboot.exception;

public class BigAgeAndLowSalaryException extends RuntimeException {
  public BigAgeAndLowSalaryException(String message){
    super(message);
  }

}
