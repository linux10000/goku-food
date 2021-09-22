package com.akiratoriyama.gokufoodapi.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.akiratoriyama.gokufoodapi.infra.filter.TokenFilter;
import com.akiratoriyama.gokufoodapi.infra.security.TokenManager;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String SUFFIX_DEEP_ALL = "/**";
	public static final String PATH_API = "/api";
	public static final String PATH_H2_CONSOLE = "/h2-console";
	public static final String PATH_ACTUATOR = "/actuator";
	public static final String PATH_OPENAPI = "/swagger-ui";
	public static final String PATH_OPENAPI_DOCS = "/v3/api-docs";
	public static final String PATH_OPENAPI_HTML = "/swagger-ui.html";
	
	@Autowired
	private TokenManager tokenManager;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin().and().cors().disable().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, PATH_API).permitAll()
			.antMatchers(Endpoint.Auth.V1.URL_TOKEN).permitAll()
			.antMatchers(PATH_H2_CONSOLE + SUFFIX_DEEP_ALL).permitAll()
			.antMatchers(PATH_ACTUATOR + SUFFIX_DEEP_ALL).permitAll()
			.antMatchers(PATH_OPENAPI + SUFFIX_DEEP_ALL, PATH_OPENAPI_DOCS + SUFFIX_DEEP_ALL, PATH_OPENAPI_HTML).permitAll()
			.antMatchers(PATH_API + SUFFIX_DEEP_ALL).permitAll()
			.anyRequest().denyAll()
			.and()
				.addFilter(new TokenFilter(authenticationManager(), tokenManager))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable();
	}
}
