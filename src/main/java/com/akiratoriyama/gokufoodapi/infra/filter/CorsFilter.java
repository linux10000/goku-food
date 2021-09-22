package com.akiratoriyama.gokufoodapi.infra.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	private static final String HEADER_ORIGIN = "origin";
	private static final String HEADER_VARY = "Origin";
	private static final String ONE_HOUR = "3600";
	private static final String ALLOWED_HEADERS = "Origin, X-Requested-With, Content-Type, Accept, Authorization";
	private static final String EXPOSE_HEADERS = "Location";

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {  
		HttpServletRequest httpReq = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1; 
        
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, httpReq.getHeader(HEADER_ORIGIN));
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.format("%s, %s, %s, %s, %s, %s", HttpMethod.POST, HttpMethod.GET, HttpMethod.PUT, HttpMethod.OPTIONS, HttpMethod.DELETE, HttpMethod.PATCH) );
		response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, ONE_HOUR);
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, EXPOSE_HEADERS);
        response.setHeader(HttpHeaders.VARY, HEADER_VARY);
        
        arg2.doFilter(arg0, arg1);
	}
}
