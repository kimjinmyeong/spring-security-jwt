package com.github.kimjinmyeong.spring_jwt_auth.application.startup;

import com.github.kimjinmyeong.spring_jwt_auth.domain.enums.UserRoleEnum;
import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import com.github.kimjinmyeong.spring_jwt_auth.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class InitData {
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User masterUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("adminpassword"))
                        .nickname("MasterAdmin")
                        .roles(new HashSet<>(Set.of(UserRoleEnum.MASTER)))
                        .build();
                userRepository.save(masterUser);
                log.info("MASTER user created with username: 'admin' and default password.");
            }
        };
    }
}