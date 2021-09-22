package com.akiratoriyama.gokufoodapi.infra.security;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser {

	private String login;
	private BigInteger userId;
	private String token;
}
