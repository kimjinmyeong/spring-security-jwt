package com.github.kimjinmyeong.spring_jwt_auth.infrastructure.repository;

import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import com.github.kimjinmyeong.spring_jwt_auth.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
