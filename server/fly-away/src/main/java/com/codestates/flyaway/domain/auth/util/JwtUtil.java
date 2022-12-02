package com.codestates.flyaway.domain.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.codestates.flyaway.domain.redis.RedisUtil;
import com.codestates.flyaway.global.exception.BusinessLogicException;
import com.codestates.flyaway.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static com.codestates.flyaway.global.exception.ExceptionCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final RedisUtil redisUtil;

    @Value("${spring.jwt.secret}")
    public String SECRET_KEY;

    public static final String PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String EMAIL = "email";
    public static final String SUBJECT = "flyaway token";

    public static final long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 30;  //30분
    public static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 3;  //3일

    /**
     * access token 생성
     */
    public String createAccessToken(String email) {
        return PREFIX + JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND))
                .withClaim(EMAIL, email)
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    /**
     * refresh token 생성
     */
    public String createRefreshToken(String email) {
        return PREFIX + JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDATION_SECOND))
                .withClaim(EMAIL, email)
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    /**
     * access token 검증
     */
    public void verifyAccessToken(String accessToken) {
        if (isExpired(accessToken)) {
            log.info("access token expired - {}", accessToken);
            throw new BusinessLogicException(ACCESS_TOKEN_EXPIRED);
        }
    }

    /**
     * refresh token 검증 (access/refresh 각각 다른 예외를 던져야 하기 떄문에 분리) -> 리팩토링 고려
     */
    public void verifyRefreshToken(String refreshToken) {
        if (isExpired(refreshToken)) {
            log.info("refresh token expired - {}", refreshToken);
            throw new BusinessLogicException(REFRESH_TOKEN_EXPIRED);
        }
    }

    /**
     * 만료 여부 확인
     */
    private boolean isExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    public Long getExpiration(String token) {
        return JWT.decode(token).getExpiresAt().getTime() - System.currentTimeMillis();
    }

    public String getPayload(String token) {
        return JWT.decode(token).getClaim(EMAIL).asString();
    }
}
