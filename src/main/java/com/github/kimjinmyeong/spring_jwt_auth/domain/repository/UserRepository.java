package com.github.kimjinmyeong.spring_jwt_auth.domain.repository;

import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}