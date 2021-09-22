package com.akiratoriyama.gokufoodapi.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.security.TokenManager;
import com.akiratoriyama.gokufoodapi.infra.security.TokenResponse;
import com.akiratoriyama.gokufoodapi.infra.security.TokenSession;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.service.AuthService;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;
import com.akiratoriyama.gokufoodapi.service.UserResourceService;
import com.akiratoriyama.gokufoodapi.service.UserService;


@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private TokenCachedService tokenCachedService;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResourceService userResourceService;
	
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	
	
	@Override
	public TokenResponse getToken(String login, String password) {
		try {
			User user = userService.findByLoginAndPassword(login, password);
			TokenResponse tokenResponse = this.tokenManager.createToken(user.getLogin(), user.getId().longValue());
			
			tokenCachedService.insert(
					TokenSession.builder()
					.login(login)
					.rawToken(tokenResponse.getAccessToken())
					.rawRefreshToken(tokenResponse.getRefreshToken())
					.userId(user.getId())
					.resources(userResourceService.findByUserId(user.getId()).stream().map(v -> v.getResourceId().toString()).collect(Collectors.toList()))
					.build()
					);
			
			return tokenResponse;
		} catch (UserNotFoundException e) {
			throw new BadCredentialsException("");
		}
	}
	
	@Override
	public TokenResponse refreshToken(String rawToken, String rawRefreshToken) {
		TokenSession session = this.tokenCachedService.getByRawToken(rawToken);
		if ( session == null || !session.getRawRefreshToken().equals(rawRefreshToken) )
			throw new BadCredentialsException("");
		
		tokenManager.verifyTokenRawWithRefreshTolerance(rawToken);
		tokenManager.verifyTokenRawWithRefreshTolerance(rawRefreshToken);
		
		TokenResponse tokenResponse =  this.tokenManager.createToken(session.getLogin(), session.getUserId().longValue());
		
		tokenCachedService.insert(
				TokenSession.builder()
				.login(session.getLogin())
				.rawToken(tokenResponse.getAccessToken())
				.rawRefreshToken(tokenResponse.getRefreshToken())
				.userId(session.getUserId())
				.resources(userResourceService.findByUserId(session.getUserId()).stream().map(v -> v.getResourceId().toString()).collect(Collectors.toList()))
				.build()
				);	
		
		this.scheduledSessionToExclude(session.getRawToken());
		
		return tokenResponse;
	}
	
	public void scheduledSessionToExclude(String rawToken) {
		executorService.schedule(() -> tokenCachedService.deleteByRawToken(rawToken), 2, TimeUnit.MINUTES);
	}
}

