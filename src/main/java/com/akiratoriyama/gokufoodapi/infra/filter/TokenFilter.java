package com.akiratoriyama.gokufoodapi.infra.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.akiratoriyama.gokufoodapi.infra.config.SecurityConfig;
import com.akiratoriyama.gokufoodapi.infra.security.AuthUser;
import com.akiratoriyama.gokufoodapi.infra.security.TokenErrorResponse;
import com.akiratoriyama.gokufoodapi.infra.security.TokenManager;
import com.akiratoriyama.gokufoodapi.infra.util.ObjectMapperUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Lists;

public class TokenFilter extends BasicAuthenticationFilter {
	private static final String BEARER_TOKEN_PREFIX = "Bearer";
	private static final String CONTENT_TYPE_JSON = "application/json";
	
	private TokenManager tokenManager;

	public TokenFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
		super(authenticationManager);
		this.tokenManager = tokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpReq, HttpServletResponse httpRes, FilterChain chain) throws IOException, ServletException {
		if (
				httpReq.getMethod().contains(HttpMethod.OPTIONS.name()) 
				|| !httpReq.getRequestURL().toString().contains(SecurityConfig.PATH_API)
		) {
			chain.doFilter(httpReq, httpRes);
			return;
		}

		AuthUser authUser = null;
		String rawToken = null;

		try {
			String authorization = httpReq.getHeader(HttpHeaders.AUTHORIZATION);
			
			if ( !StringUtils.isBlank(authorization) ) 
				rawToken = authorization.replace(BEARER_TOKEN_PREFIX, "").trim();
			
			
			if ( StringUtils.isBlank(rawToken) ) {
				chain.doFilter(httpReq, httpRes);
				return;
			}
			
			DecodedJWT jwt = tokenManager.verifyTokenRawWithRefreshTolerance(rawToken);
			
			authUser = AuthUser.builder()
					.login(jwt.getClaim(TokenManager.CLAIMS.LOGIN.getValue()).as(String.class))
					.userId(BigInteger.valueOf(jwt.getClaim(TokenManager.CLAIMS.USERID.getValue()).as(Long.class)))
					.token(rawToken)
					.build();
			
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authUser, null, Lists.newArrayList()));
			chain.doFilter(httpReq, httpRes);
		} catch (BadCredentialsException | TokenExpiredException e1) {
			httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpRes.setContentType(CONTENT_TYPE_JSON);

			PrintWriter out = httpRes.getWriter();
			out.print(
					ObjectMapperUtil.getObjectMapper().writeValueAsString(
							TokenErrorResponse.builder()
							.message(e1.getMessage())
							.exception(e1.getClass().getName())
							.build()
							)
					);
			out.flush();
		} catch (Exception e) {
			httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpRes.setContentType(CONTENT_TYPE_JSON);

			PrintWriter out = httpRes.getWriter();
			out.print(
					ObjectMapperUtil.getObjectMapper().writeValueAsString(
							TokenErrorResponse.builder()
							.message(e.getMessage())
							.exception(e.getClass().getName())
							.build()
							)
					);
			out.flush();
		}
	}
}
