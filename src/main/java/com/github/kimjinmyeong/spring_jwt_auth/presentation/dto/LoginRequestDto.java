package com.github.kimjinmyeong.spring_jwt_auth.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}