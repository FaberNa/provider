package com.example.demo.provider.exception;

public class NotaNotFoundException extends RuntimeException {
    public NotaNotFoundException(String message) {
        super(message);
    }
}

