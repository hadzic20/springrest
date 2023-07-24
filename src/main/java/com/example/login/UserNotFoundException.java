package com.example.login;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException() {
        super(" kullanıcı bulunamadı");
    }
}
