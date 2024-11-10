package com.github.kimjinmyeong.spring_jwt_auth.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    @Schema(description = "The username of the user", example = "john_doe")
    private String username;

    @NotBlank
    @Schema(description = "The password of the user", example = "password123")
    private String password;
}