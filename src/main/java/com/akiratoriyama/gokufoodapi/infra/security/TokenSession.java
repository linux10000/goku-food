package com.akiratoriyama.gokufoodapi.infra.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSession implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String rawToken;
	private String rawRefreshToken;
	private String login;
	private BigInteger userId;
	private List<String> resources;
}
