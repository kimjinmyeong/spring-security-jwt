package com.github.kimjinmyeong.spring_jwt_auth.infrastructure.repository;

import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import com.github.kimjinmyeong.spring_jwt_auth.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    @NonNull
    <S extends User> S save(@NonNull S entity);
}
