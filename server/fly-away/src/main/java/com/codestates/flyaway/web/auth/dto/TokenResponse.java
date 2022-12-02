package com.codestates.flyaway.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
