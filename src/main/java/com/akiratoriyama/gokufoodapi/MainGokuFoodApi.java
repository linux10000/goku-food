package com.akiratoriyama.gokufoodapi;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.akiratoriyama.gokufoodapi.infra.util.DateUtil;

@SpringBootApplication
public class MainGokuFoodApi {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(DateUtil.DEFAULT_TIMEZONE));		
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
		
		SpringApplication.run(MainGokuFoodApi.class, args);
	}
}
