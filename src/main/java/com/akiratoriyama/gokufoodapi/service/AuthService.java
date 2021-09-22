package com.akiratoriyama.gokufoodapi.service;

import javax.validation.constraints.NotBlank;

import com.akiratoriyama.gokufoodapi.infra.security.TokenResponse;

public interface AuthService {

	TokenResponse getToken(@NotBlank String login, @NotBlank String password);

	TokenResponse refreshToken(@NotBlank String rawToken, @NotBlank String rawRefreshToken);

}
