package com.example.login;

public class PasswordErrorException extends RuntimeException {
    PasswordErrorException(String message) {
        super(message);
    }
}
