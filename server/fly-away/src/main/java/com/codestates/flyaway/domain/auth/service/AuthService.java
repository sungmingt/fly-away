package com.codestates.flyaway.domain.auth.service;

import com.codestates.flyaway.domain.auth.util.JwtUtil;
import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.redis.RedisUtil;
import com.codestates.flyaway.global.exception.BusinessLogicException;
import com.codestates.flyaway.web.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codestates.flyaway.domain.member.util.MemberUtil.*;
import static com.codestates.flyaway.global.exception.ExceptionCode.*;
import static com.codestates.flyaway.web.auth.dto.LoginDto.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    /** 로그인
     * @param loginRequest
     */
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        //이메일 확인
        Member member = findByEmail(email);

        //비밀번호 확인
        if (checkPassword(password, member.getPassword())) {
            String accessToken = jwtUtil.createAccessToken(email);
            String refreshToken = jwtUtil.createRefreshToken(email);

            redisUtil.setDataExpire(email, refreshToken);
            return new LoginResponse(accessToken, refreshToken, String.valueOf(member.getId()));
        }

        throw new BusinessLogicException(PASSWORD_NOT_MATCH);
    }

    /**
     * 로그아웃
     * @param email
     * @param accessToken
     */
    public void logout(String email, String accessToken) {
        //refresh token 삭제
        redisUtil.deleteData(email);

        //access token blacklist 등록
        Long expiration = jwtUtil.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, expiration);
    }

    /**
     * 비밀번호 검증
     * @param input
     * @param exact
     */
    private boolean checkPassword(String input, String exact) {
        return encode(input).equals(exact);
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(EMAIL_NOT_EXISTS));
    }

    /**
     * access token 재발급
     * @param refreshToken
     */
    public TokenResponse reissue(String refreshToken) {
        //refresh token 만료 여부 검증
        jwtUtil.verifyRefreshToken(refreshToken);

        String email = jwtUtil.getPayload(refreshToken);
        String exRefreshToken = redisUtil.getData(email);

        //refreshToken 유효성 검증
        jwtUtil.checkSameness(refreshToken, exRefreshToken);

        //access/refresh token 재발급
        String accessToken = jwtUtil.createAccessToken(email);
        String newRefreshToken = jwtUtil.createRefreshToken(email);
        redisUtil.setDataExpire(email, newRefreshToken);

        return new TokenResponse(accessToken, newRefreshToken);
    }
}
