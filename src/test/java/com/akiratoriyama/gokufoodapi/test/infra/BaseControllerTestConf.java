package com.akiratoriyama.gokufoodapi.test.infra;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import com.akiratoriyama.gokufoodapi.infra.security.GokuResourceBasedDecisionVoter;
import com.akiratoriyama.gokufoodapi.infra.security.TokenManager;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;
import com.akiratoriyama.gokufoodapi.service.impl.TokenCachedServiceImpl;


public abstract class BaseControllerTestConf extends BaseTestConf {

	@Bean
	public GokuResourceBasedDecisionVoter gokuResourceBasedDecisionVoter() {
		return new GokuResourceBasedDecisionVoter();
	}
	
	@Bean
	public TokenCachedService TokenCachedService(CacheManager cacheManager) {
		return new TokenCachedServiceImpl(cacheManager);
	}
	
	@Bean
	public TokenManager tokenManager() {
		return new TokenManager();
	}
}
