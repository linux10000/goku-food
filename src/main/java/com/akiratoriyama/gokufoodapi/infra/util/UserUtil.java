package com.akiratoriyama.gokufoodapi.infra.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.infra.security.AuthUser;

@Component
public class UserUtil {

	public Optional<AuthUser> getCurrent() {
        return
                this.getAuthentication()
                    .filter(auth -> auth.getPrincipal() instanceof AuthUser)
                    .map(auth -> (AuthUser) auth.getPrincipal());
	}
	
    private Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }
}
