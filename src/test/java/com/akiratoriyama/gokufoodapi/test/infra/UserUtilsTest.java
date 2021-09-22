package com.akiratoriyama.gokufoodapi.test.infra;

import java.math.BigInteger;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.akiratoriyama.gokufoodapi.infra.security.AuthUser;

public class UserUtilsTest {

	public static void injectAdminUserOnContext() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(
						AuthUser.builder()
						.login("login")
						.userId(BigInteger.valueOf(-1))
						.token("")
						.build(), 
						"", 
						List.of()
						)
				);
	}
}
