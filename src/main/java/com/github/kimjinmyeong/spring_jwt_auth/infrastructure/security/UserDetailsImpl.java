package com.github.kimjinmyeong.spring_jwt_auth.infrastructure.security;

import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.UserPrincipalDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final UserPrincipalDto userPrincipalDto;

    public final static String ROLE_PREFIX = "ROLE_";

    public UserDetailsImpl(UserPrincipalDto userPrincipalDto) {
        this.userPrincipalDto = userPrincipalDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userPrincipalDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPrincipalDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userPrincipalDto.getUsername();
    }

}
