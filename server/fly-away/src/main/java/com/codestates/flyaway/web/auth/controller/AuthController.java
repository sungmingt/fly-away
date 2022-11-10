package com.codestates.flyaway.web.auth.controller;

import com.codestates.flyaway.domain.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.codestates.flyaway.domain.auth.util.JwtUtil.AUTHORIZATION;
import static com.codestates.flyaway.domain.auth.util.JwtUtil.PREFIX;
import static com.codestates.flyaway.web.auth.dto.LoginDto.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "로그인 API")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginInfo = authService.login(loginRequest);

        response.addHeader("memberId", String.valueOf(loginInfo.getMemberId()));
        response.addHeader(AUTHORIZATION, loginInfo.getAccessToken());

        return ("로그인 성공");
    }

    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        String accessToken = request.getHeader(AUTHORIZATION).replace(PREFIX, "");
        log.info("### 로그아웃 요청 - {}", email);

        authService.logout(email, accessToken);
        return "로그아웃 성공";
    }
}
