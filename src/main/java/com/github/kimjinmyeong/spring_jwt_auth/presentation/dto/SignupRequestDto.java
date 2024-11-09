package com.github.kimjinmyeong.spring_jwt_auth.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Schema(description = "The username for the new account", example = "john_doe")
    private String username;

    @NotBlank
    @Schema(description = "The password for the new account", example = "password123")
    private String password;

    @NotBlank
    @Schema(description = "The nickname of the user", example = "Johnny")
    private String nickname;

}