package org.example.day7springboot.exception;

public class NotAmongLegalAgeException extends RuntimeException{

  public NotAmongLegalAgeException(String message) {
    super(message);
  }
}
