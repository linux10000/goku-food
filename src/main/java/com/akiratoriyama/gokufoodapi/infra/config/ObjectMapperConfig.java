package com.akiratoriyama.gokufoodapi.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.akiratoriyama.gokufoodapi.infra.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfig {

	@Bean()
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		return ObjectMapperUtil.getObjectMapper();
	}
}
