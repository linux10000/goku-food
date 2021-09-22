package com.akiratoriyama.gokufoodapi.test.infra;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DataJpaTest
@Transactional
@EnableJpaAuditing(dateTimeProviderRef = Const.Bean.AUDITING_DATETIME_PROVIDER)
public @interface TestDataJpa {

}
