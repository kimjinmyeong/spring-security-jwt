package com.github.kimjinmyeong.spring_jwt_auth.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kimjinmyeong.spring_jwt_auth.application.service.AuthService;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.LoginRequestDto;
import com.github.kimjinmyeong.spring_jwt_auth.presentation.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSignup_Success() throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto("testuser", "password123", "testnickname");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testSignup_ValidationError() throws Exception {
        SignupRequestDto signupRequestDto = new SignupRequestDto("", "password123", "testnickname");

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_Success() throws Exception {
        // First, sign up the user
        SignupRequestDto signupRequestDto = new SignupRequestDto("testuser2", "password123", "testnickname2");
        authService.signup(signupRequestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto("testuser2", "password123");

        mockMvc.perform(post("/sign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto("nonexistentuser", "password123");

        mockMvc.perform(post("/sign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "MASTER")
    public void testAdminAccess_WithMasterRole() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Access granted to MASTER role."));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAdminAccess_WithUserRole() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUserAccess_WithUserRole() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Access granted to USER role."));
    }

    @Test
    @WithMockUser(roles = "MASTER")
    public void testUserAccess_WithMasterRole() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAdminAccess_WithoutAuthentication() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUserAccess_WithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
    }
}