package org.example.day7springboot.service;

public class NotAmongLegalAgeException extends RuntimeException{

  public NotAmongLegalAgeException(String message) {
    super(message);
  }
}
