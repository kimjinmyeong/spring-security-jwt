package com.github.kimjinmyeong.spring_jwt_auth.presentation.controller;

import com.github.kimjinmyeong.spring_jwt_auth.application.service.AuthService;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.LoginResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.SignupResponseDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.LoginRequestDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.SignupRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User Signup", description = "Create a new user account.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @Parameter(description = "Signup request data", required = true)
            @Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponse = authService.signup(signupRequestDto);
        return ResponseEntity.ok(signupResponse);
    }

    @Operation(summary = "User Login", description = "Authenticate a user and return a JWT token.")
    @PostMapping("/sign")
    public ResponseEntity<LoginResponseDto> login(
            @Parameter(description = "Login request data", required = true)
            @Valid @RequestBody LoginRequestDto loginRequest,
            HttpServletResponse response) {
        LoginResponseDto loginResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Admin Only Access", description = "Access restricted to users with MASTER role.")
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Access granted to MASTER role.");
    }

    @Operation(summary = "User Only Access", description = "Access restricted to users with USER role.")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("Access granted to USER role.");
    }

}