package com.akiratoriyama.gokufoodapi.infra.security;

import java.math.BigInteger;
import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.infra.util.UserUtil;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;

@Component
public class GokuResourceBasedDecisionVoter implements AccessDecisionVoter<MethodInvocation> {
	private static final BigInteger ADMIN_USERID = BigInteger.valueOf(-1);
	
	@Autowired
	private TokenCachedService tokenService;
	
	@Autowired
	private UserUtil userUtil;
	
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MethodInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
		AuthUser authUser = userUtil.getCurrent().orElseThrow(() -> new BadCredentialsException(""));
		
		if ( authUser.getUserId().equals(ADMIN_USERID) )
			return ACCESS_GRANTED;
			
		
		TokenSession session = tokenService.getByRawToken(authUser.getToken());
		if ( session == null )
			throw new BadCredentialsException("");
		
		
		if ( attributes.stream().noneMatch(v ->  session.getResources().contains(v.getAttribute())) )
			return ACCESS_DENIED;
		
		
		return ACCESS_GRANTED;
	}

}
