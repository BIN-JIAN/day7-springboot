package org.example.day7springboot.exception;

public class EmployeeStatusException extends RuntimeException {
    public EmployeeStatusException(String message) {
        super(message);
    }
}

