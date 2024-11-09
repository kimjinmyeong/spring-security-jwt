package com.github.kimjinmyeong.spring_jwt_auth.application.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SignupResponseDto {

    @Schema(description = "The username of the newly created account")
    private String username;

    @Schema(description = "The nickname of the newly created account")
    private String nickname;

    @Schema(description = "List of authorities assigned to the user")
    private List<Authority> authorities;

    @Getter
    @AllArgsConstructor
    public static class Authority {
        @Schema(description = "The name of the authority")
        private String authorityName;
    }

}
