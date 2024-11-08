package com.github.kimjinmyeong.spring_jwt_auth.application.service.dto;

import com.github.kimjinmyeong.spring_jwt_auth.domain.enums.UserRoleEnum;
import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserPrincipalDto {

    private String username;

    private String password;

    private Set<UserRoleEnum> roles;

    public static UserPrincipalDto fromEntity(User user) {
        return UserPrincipalDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}

