package com.github.kimjinmyeong.spring_jwt_auth.infrastructure.security;

import com.github.kimjinmyeong.spring_jwt_auth.application.service.dto.UserPrincipalDto;
import com.github.kimjinmyeong.spring_jwt_auth.domain.model.User;
import com.github.kimjinmyeong.spring_jwt_auth.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(UserPrincipalDto.fromEntity(user));
    }
}
