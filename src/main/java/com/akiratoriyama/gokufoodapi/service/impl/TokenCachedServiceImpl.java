package com.akiratoriyama.gokufoodapi.service.impl;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.akiratoriyama.gokufoodapi.infra.security.TokenSession;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;

@Service
public class TokenCachedServiceImpl implements TokenCachedService {

	private Cache tokenCache;
	
	public TokenCachedServiceImpl(CacheManager cacheManager) {
		this.tokenCache = cacheManager.getCache(Const.Cache.TOKEN);
	}
	
	@Override
	public TokenSession getByRawToken(String rawToken) {
		return tokenCache.get(rawToken, TokenSession.class);
	}
	
	@Override
	public void insert(TokenSession tr) {
		tokenCache.put(tr.getRawToken(), tr);
	}
	
	@Override
	public void deleteByRawToken(String rawToken) {
		tokenCache.evictIfPresent(rawToken);
	}

}
