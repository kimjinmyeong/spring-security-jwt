package com.github.kimjinmyeong.spring_jwt_auth.application.util;

import com.github.kimjinmyeong.spring_jwt_auth.domain.enums.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.expiration-time-ms}")
    private long expirationTimeMs;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * Creates a JWT token with the specified username and roles.
     *
     * @param username the username of the user
     * @param roles    the roles of the user
     * @return the JWT token
     */
    public String createToken(String username, Set<UserRoleEnum> roles) {
        Date now = new Date();
        Map<String, Object> claims = Map.of("roles",  roles.stream()
                .map(UserRoleEnum::name)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(new Date(now.getTime() + expirationTimeMs))
                .setIssuedAt(now)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    /**
     * Resolves the JWT token from the Authorization header in the request.
     *
     * @param request HttpServletRequest object.
     * @return the JWT token without the Bearer prefix, or null if not present
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * Validates the specified JWT token.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username, or null if extraction fails
     */
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            log.error("Failed to extract username from token: {}", e.getMessage());
            return null;
        }
    }

}