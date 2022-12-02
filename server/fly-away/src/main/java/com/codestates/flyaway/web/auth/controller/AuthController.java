package com.codestates.flyaway.web.auth.controller;

import com.codestates.flyaway.domain.auth.service.AuthService;
import com.codestates.flyaway.web.auth.dto.TokenResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.codestates.flyaway.domain.auth.util.JwtUtil.*;
import static com.codestates.flyaway.web.auth.dto.LoginDto.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "로그인 API")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest);
    }

    @ApiOperation(value = "로그아웃 API")
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String email = (String) request.getAttribute(EMAIL);
        String accessToken = request.getHeader(AUTHORIZATION).replace(PREFIX, "");

        authService.logout(email, accessToken);
        return "로그아웃 성공";
    }

    @ApiOperation(value = "access token 재발급 요청")
    @GetMapping("/reissue")
    public TokenResponse reissue(HttpServletRequest request){
        String refreshToken = request.getHeader(AUTHORIZATION).replace(PREFIX, "");
        return authService.reissue(refreshToken);
    }
}
