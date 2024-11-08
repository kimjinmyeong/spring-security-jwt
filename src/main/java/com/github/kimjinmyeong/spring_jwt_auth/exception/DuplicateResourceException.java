package com.github.kimjinmyeong.spring_jwt_auth.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
