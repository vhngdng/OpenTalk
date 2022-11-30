package com.example.demo.exception;

public class MailScheduleException extends RuntimeException{
    public MailScheduleException() {
    }

    public MailScheduleException(String message) {
        super(message);
    }

    public MailScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}
