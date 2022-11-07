package com.codestates.flyaway.auth;

import com.auth0.jwt.JWT;
import com.codestates.flyaway.domain.login.service.LoginService;
import com.codestates.flyaway.domain.login.util.JwtUtil;
import com.codestates.flyaway.domain.member.entity.Member;
import com.codestates.flyaway.domain.member.repository.MemberRepository;
import com.codestates.flyaway.domain.redis.RedisUtil;
import com.codestates.flyaway.web.login.dto.LoginDto.LoginRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codestates.flyaway.domain.login.util.JwtUtil.EMAIL;
import static com.codestates.flyaway.domain.login.util.JwtUtil.PREFIX;
import static com.codestates.flyaway.domain.member.util.MemberUtil.encode;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @BeforeEach
    void beforeAll() {
        Member member = new Member("jordan", "jordan123@gmail.com", encode("jd1234!@"));
        memberRepository.save(member);
    }

    @DisplayName("로그인 - access/refresh token")
    @Test
    @Order(1)
    void loginTest() {
        //given
        LoginRequest request = new LoginRequest( "jordan123@gmail.com", "jd1234!@");

        //when
        String accessToken = loginService.login(request);

        //then
        String token = accessToken.replace(PREFIX, "");
        String refreshToken = redisUtil.getData(request.getEmail());

        //access 토큰이 유효해야 한다.
        assertThat(JWT.decode(token).getClaim(EMAIL).asString())
                .isEqualTo(request.getEmail());

        //refresh 토큰이 DB에 저장되어야 한다.
        assertThat(refreshToken).isNotNull();

        //refresh 토큰이 유효해야 한다.
        assertThat(JWT.decode(refreshToken).getClaim(EMAIL).asString())
                .isEqualTo(request.getEmail());
    }

    @DisplayName("로그아웃 - (access token 블랙리스트 등록, refresh token 삭제)")
    @Test
    void logoutTest() {
    }
}