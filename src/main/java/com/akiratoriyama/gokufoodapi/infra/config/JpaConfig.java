package com.akiratoriyama.gokufoodapi.infra.config;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

@Configuration
@EnableJpaRepositories(basePackages = "com.akiratoriyama.gokufoodapi.repository")
@EnableJpaAuditing(dateTimeProviderRef = Const.Bean.AUDITING_DATETIME_PROVIDER)
public class JpaConfig {

	@Bean(name = Const.Bean.AUDITING_DATETIME_PROVIDER)
	public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
	}
}
