package com.codestates.flyaway.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginDto {

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private String memberId;
    }
}
