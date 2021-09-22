package com.akiratoriyama.gokufoodapi.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.akiratoriyama.gokufoodapi.controller.request.AuthCredentialsRequest;
import com.akiratoriyama.gokufoodapi.controller.request.AuthRefreshRequest;
import com.akiratoriyama.gokufoodapi.infra.security.AuthUser;
import com.akiratoriyama.gokufoodapi.infra.security.TokenResponse;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.UserUtil;
import com.akiratoriyama.gokufoodapi.service.AuthService;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TokenCachedService tokenCachedService;
	
	@Autowired
	private UserUtil userUtil;

    @PostMapping(Endpoint.Auth.V1.URL_TOKEN)
    public TokenResponse getToken(@Valid @RequestBody AuthCredentialsRequest sto) {
    	return authService.getToken(sto.getLogin(), sto.getPassword());
    }
    
    @PostMapping(Endpoint.Auth.V1.URL_REFRESH_TOKEN)
    public TokenResponse refreshToken(@Valid @RequestBody AuthRefreshRequest sto) {
    	return authService.refreshToken(userUtil.getCurrent().map(AuthUser::getToken).orElse(null), sto.getRefreshToken());
    }
    
    @PostMapping(Endpoint.Auth.V1.URL_LOGOUT)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void logout() {
    	this.tokenCachedService.deleteByRawToken(userUtil.getCurrent().map(AuthUser::getToken).orElseThrow(() -> new BadCredentialsException("")));
    }
}
