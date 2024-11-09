package com.github.kimjinmyeong.spring_jwt_auth.application.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(description = "The JWT token for authentication")
    private String token;
}