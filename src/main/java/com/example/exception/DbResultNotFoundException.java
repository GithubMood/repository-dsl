package com.example.exception;

public class DbResultNotFoundException extends RuntimeException {
    public DbResultNotFoundException(String message) {
        super(message);
    }
}
