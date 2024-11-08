package com.github.kimjinmyeong.spring_jwt_auth.presentation.controller;

import com.github.kimjinmyeong.spring_jwt_auth.application.service.AuthService;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.LoginResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.SignupResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.LoginRequestDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.SignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user signup requests.
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponse = authService.signup(signupRequestDto);
        return ResponseEntity.ok(signupResponse);
    }

    /**
     * Handles user login requests.
     */
    @PostMapping("/sign")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

}
