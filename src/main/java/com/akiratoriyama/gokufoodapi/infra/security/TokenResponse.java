package com.akiratoriyama.gokufoodapi.infra.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

	private String accessToken;
	private String tokenType;
	private Integer expiresIn;
	private String refreshToken;
	private Long userId;
}
