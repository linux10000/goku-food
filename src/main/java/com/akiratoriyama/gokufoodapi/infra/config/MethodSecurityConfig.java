package com.akiratoriyama.gokufoodapi.infra.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.akiratoriyama.gokufoodapi.infra.security.GokuResourceBasedDecisionVoter;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	@Autowired
	private GokuResourceBasedDecisionVoter gokuResourceBasedDecisionVoter;

	@Override
	protected AccessDecisionManager accessDecisionManager() {
	    return new UnanimousBased(List.of(gokuResourceBasedDecisionVoter));
	}
}
