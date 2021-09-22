package com.akiratoriyama.gokufoodapi.controller.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.akiratoriyama.gokufoodapi.model.Resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResourceResponse {

	private BigInteger id;
	private LocalDateTime ts;
	private Resource resource;
	private User user;
	
	@Data
	@Builder
	public static class User {
		private BigInteger id;
		private String login;
		private String firstName;
		private String lastName;
	}
}
