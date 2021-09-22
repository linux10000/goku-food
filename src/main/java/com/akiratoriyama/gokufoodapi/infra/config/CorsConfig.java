package com.akiratoriyama.gokufoodapi.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.akiratoriyama.gokufoodapi.infra.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter hcfCorsFilter() {
		return new CorsFilter();
	}
}
