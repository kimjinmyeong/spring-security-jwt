package com.github.kimjinmyeong.spring_jwt_auth.application.service;

import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.LoginResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.SignupResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.domain.enums.UserRoleEnum;
import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import com.github.kimjinmyeong.spring_jwt_auth.domain.repository.UserRepository;
import com.github.kimjinmyeong.spring_jwt_auth.application.util.JwtUtil;
import com.github.kimjinmyeong.spring_jwt_auth.exception.DuplicateResourceException;
import com.github.kimjinmyeong.spring_jwt_auth.exception.ErrorMessage;
import com.github.kimjinmyeong.spring_jwt_auth.exception.UnAuthorizationException;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.LoginRequestDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.github.kimjinmyeong.spring_jwt_auth.application.util.JwtUtil.BEARER_PREFIX;
import static com.github.kimjinmyeong.spring_jwt_auth.infrastructure.security.UserDetailsImpl.ROLE_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto request) {
        log.info("Attempting to sign up user: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            log.warn("Nickname already exists: {}", request.getNickname());
            throw new DuplicateResourceException("Nickname already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(UserRoleEnum.USER))
                .build();

        userRepository.save(user);
        log.info("User successfully signed up with username: {}", user.getUsername());

        return SignupResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(Collections.singletonList(new SignupResponseDto.Authority(ROLE_PREFIX + UserRoleEnum.USER)))
                .build();
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse response) {
        log.info("Attempting to log in user: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login failed: Invalid username provided: {}", request.getUsername());
                    return new UnAuthorizationException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD.getMessage());
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed: Invalid password for username: {}", request.getUsername());
            throw new UnAuthorizationException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD.getMessage());
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRoles());
        log.info("User logged in successfully: {}, token issued", user.getUsername());

        response.setHeader("Authorization", BEARER_PREFIX + token);
        return new LoginResponseDto(token);
    }

}