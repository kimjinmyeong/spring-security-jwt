package com.github.kimjinmyeong.spring_jwt_auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
