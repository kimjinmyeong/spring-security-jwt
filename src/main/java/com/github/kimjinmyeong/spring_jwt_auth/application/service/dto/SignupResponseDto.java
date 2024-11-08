package com.github.kimjinmyeong.spring_jwt_auth.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SignupResponseDto {

    private String username;
    private String nickname;
    private List<Authority> authorities;

    @Getter
    @AllArgsConstructor
    public static class Authority {
        private String authorityName;
    }

}
