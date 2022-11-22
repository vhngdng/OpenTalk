package com.example.demo.exception;

public class DuplicateEntityException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DuplicateEntityException(final String message) {
        super(message);
    }
}
