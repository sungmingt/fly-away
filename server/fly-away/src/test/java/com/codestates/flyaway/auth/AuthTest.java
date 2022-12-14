package com.codestates.flyaway.auth;

import com.auth0.jwt.JWT;
import com.codestates.flyaway.domain.auth.service.AuthService;
import com.codestates.flyaway.domain.auth.util.JwtUtil;
import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.redis.RedisUtil;
import com.codestates.flyaway.web.auth.dto.LoginDto.LoginRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static com.codestates.flyaway.domain.auth.util.JwtUtil.EMAIL;
import static com.codestates.flyaway.domain.auth.util.JwtUtil.PREFIX;
import static com.codestates.flyaway.domain.member.util.MemberUtil.encode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest
class AuthTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    private String accessToken;
    private String refreshToken;

    @DisplayName("로그인/로그아웃 - access/refresh token 검증")
    @TestFactory
    Stream<DynamicTest> joinAndLogin() {
        final String name = "jordan";
        final String email = "jordan123@gmail.com";
        final String password = "jd1234!@";

        Member member = new Member(1L, name, email, encode(password));
        memberRepository.save(member);

        return Stream.of(
                dynamicTest("로그인 - access/refresh token 생성 + refresh token DB 저장", () -> {
                    //given
                    LoginRequest request = new LoginRequest(email, password);

                    //when
                    String accToken = authService.login(request).getAccessToken();
                    String refToken = authService.login(request).getRefreshToken();

                    //then
                    accessToken = accToken.replace(PREFIX, "");
                    refreshToken = refToken.replace(PREFIX, "");
                    String refreshTokenFromDB = redisUtil.getData(request.getEmail()).replace(PREFIX, "");

                    //access 토큰이 유효해야 한다.
                    assertThat(JWT.decode(accessToken).getClaim(EMAIL).asString())
                            .isEqualTo(request.getEmail());

                    //refresh 토큰이 DB에 저장되어야 한다.
                    assertThat(refreshTokenFromDB).isNotNull();

                    //refresh 토큰이 유효해야 한다.
                    assertThat(JWT.decode(refreshToken).getClaim(EMAIL).asString())
                            .isEqualTo(request.getEmail());
                }),

                dynamicTest("로그아웃 - access token 블랙리스트 등록, refresh token 삭제", () -> {
                    //when
                    authService.logout(email, accessToken);

                    //then
                    //블랙리스트 등록
                    assertThat(redisUtil.isBlacklist(accessToken)).isTrue();
                    //refresh token 삭제
                    assertThat(redisUtil.getData(email)).isNullOrEmpty();
                })
        );
    }
}
