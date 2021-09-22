package com.akiratoriyama.gokufoodapi.test.infra;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

public abstract class BaseTestConf {

	@Bean(name = Const.Bean.AUDITING_DATETIME_PROVIDER)
	public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
	}
}
