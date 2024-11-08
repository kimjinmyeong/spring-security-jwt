package com.github.kimjinmyeong.spring_jwt_auth.exception;

public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException(String message) { super(message); }
}
