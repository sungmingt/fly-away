package com.codestates.flyaway.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class LoginDto {

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        @Email
        @NotEmpty
        private String email;
        @NotEmpty
        private String password;
    }

    @Getter
    @AllArgsConstructor @NoArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private String memberId;
    }
}
