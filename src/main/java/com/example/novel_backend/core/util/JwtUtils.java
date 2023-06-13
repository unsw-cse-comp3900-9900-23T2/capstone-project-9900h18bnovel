package com.example.novel_backend.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * JWT Tools
 * @author :Contanstin
 * @create 2023/6/12 21:33
 */
@ConditionalOnProperty("novel.jwt.secret")
@Component
@Slf4j
public class JwtUtils {
    /**
     * Inject JWT encryption key
     */
    @Value("${novel.jwt.secret}")
    private String secret;

    /**
     * Define system identification header constants
     */
    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";

    /**
     * JWT generation based on user ID
     *
     * @param uid       User ID
     * @param systemKey System identification
     * @return JWT
     */
    public String generateToken(Long uid, String systemKey) {
        return Jwts.builder()
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                .setSubject(uid.toString())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * Parsing a JWT to return a user ID
     *
     * @param token     JWT
     * @param systemKey System identification
     * @return User ID
     */
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            // OK, we can trust this JWT
            // Determining if the JWT belongs to the specified system
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getSubject());
            }
        } catch (JwtException e) {
            log.warn("JWT Failed to parse:{}", token);
            // don't trust the JWT!
        }
        return null;
    }

}
