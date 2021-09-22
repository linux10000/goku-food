package com.akiratoriyama.gokufoodapi.infra.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class TokenManager {

	public static final String TOKEN_TYPE = "Bearer"; 
	public static final String TOKEN_SECRET = "X,YMNN8p_h;Z{vRrnD6JdHhvTk!5~jaH$@8;G58[5k\"j5RsZm_&ewg}ME/vLVj$H"; 
	public static final int TOKEN_DURATION_IN_SECONDS = 3600; 
	public static final int REFRESH_TOKEN_DURATION_IN_SECONDS = 3600; 
	public static final int TOKEN_TOLERANCE_IN_SECONDS = 600;
	
	private Algorithm algorithmToken;
	private JWTVerifier verifierTokenNoExpirationTolerance;
	private JWTVerifier verifierTokenWithRefreshTolerance;
	
	@PostConstruct
	public void init() {
		this.algorithmToken = Algorithm.HMAC512(TOKEN_SECRET);
		
		verifierTokenNoExpirationTolerance = JWT
				.require(algorithmToken)
				.build();		
		
		verifierTokenWithRefreshTolerance = JWT
				.require(algorithmToken)
				.acceptLeeway(TOKEN_TOLERANCE_IN_SECONDS)
				.build();	
	}
	
	public DecodedJWT verifyTokenRawNoExpirationTolerance(@NotNull String token) {
		return verifierTokenNoExpirationTolerance.verify(token);
	}
	
	public DecodedJWT verifyTokenRawWithRefreshTolerance(@NotNull String token) {
		return verifierTokenWithRefreshTolerance.verify(token);
	}
	
	public TokenResponse createToken(@NotNull String login, @NotNull Long userId) {
		LocalDateTime expireDate = LocalDateTime.now().plusSeconds(TOKEN_DURATION_IN_SECONDS);

		return 
				TokenResponse.builder()
				.expiresIn(calculateETAexpiration(expireDate).intValue())
				.tokenType(TOKEN_TYPE)
				.userId(userId)
				.refreshToken(
						JWT.create()
						.withExpiresAt(Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant()))
						.sign(algorithmToken)
				)		
				.accessToken(
						JWT.create()
						.withExpiresAt(Date.from(expireDate.atZone(ZoneId.systemDefault()).toInstant()))
						.withClaim(CLAIMS.LOGIN.value, login)
						.withClaim(CLAIMS.USERID.value, userId)
						.sign(algorithmToken)
						)
				.build();
	}
	
	private Long calculateETAexpiration(@NotNull LocalDateTime expireDateTime) {
		return ChronoUnit.SECONDS.between(LocalDateTime.now(), expireDateTime);
	}
	
	public enum CLAIMS {
		LOGIN("login"),
		USERID("userId");
		
		private final String value; 
		CLAIMS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
