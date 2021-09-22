package com.akiratoriyama.gokufoodapi.infra.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.akiratoriyama.gokufoodapi.infra.security.TokenManager;
import com.akiratoriyama.gokufoodapi.infra.util.Const;

import lombok.Builder;
import lombok.Getter;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.TransactionalMode;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

@Configuration
@EnableCaching
public class CacheConfig extends JCacheConfigurerSupport {

	public static final String EH_CACHE_MANAGER = "ehCacheManager";

	@Bean(name = EH_CACHE_MANAGER) 
	@Override
	public CacheManager cacheManager() {
		net.sf.ehcache.config.Configuration cacheManagerConfig = new net.sf.ehcache.config.Configuration()
				.diskStore(new DiskStoreConfiguration().path(Const.Misc.TEMP_FOLDER));
		
		cacheManagerConfig.addCache(this.createDefaultCache(
				CacheProps.builder()
				.cacheName(Const.Cache.ZIP_CODE)
				.diskPersistent(false)
				.overflowToDisk(true)
				.idleInSeconds(0)
				.timeToLiveInSeconds(3600)
				.maxEntriesInDisk(20000)
				.maxEntriesInMemory(10000)
				.build()
			));
		
		cacheManagerConfig.addCache(this.createDefaultCache(
				CacheProps.builder()
				.cacheName(Const.Cache.TOKEN)
				.diskPersistent(false)
				.overflowToDisk(true)
				.idleInSeconds(0)
				.timeToLiveInSeconds(TokenManager.TOKEN_DURATION_IN_SECONDS + TokenManager.TOKEN_TOLERANCE_IN_SECONDS)
				.maxEntriesInDisk(20000)
				.maxEntriesInMemory(10000)
				.build()
				));
		
		return new org.springframework.cache.ehcache.EhCacheCacheManager(new net.sf.ehcache.CacheManager(cacheManagerConfig));
	}
	
	@SuppressWarnings("deprecation")
	private CacheConfiguration createDefaultCache(CacheProps props) {
		return 
				new CacheConfiguration()
				.name(props.getCacheName())
				.eternal(false)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				.maxEntriesLocalHeap(props.getMaxEntriesInMemory())
				.maxEntriesLocalDisk(props.getMaxEntriesInDisk())
				.timeToIdleSeconds(props.getIdleInSeconds())
				.timeToLiveSeconds(props.getTimeToLiveInSeconds())
				.transactionalMode(TransactionalMode.OFF)
				.diskPersistent(props.isDiskPersistent())
				.overflowToDisk(props.isOverflowToDisk());
	}
	
	@Getter
	@Builder
	static class CacheProps {
		private String cacheName;
		private int maxEntriesInMemory;
		private int maxEntriesInDisk;
		private int idleInSeconds;
		private int timeToLiveInSeconds;
		private boolean diskPersistent;
		private boolean overflowToDisk;
	}
}
