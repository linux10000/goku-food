package com.akiratoriyama.gokufoodapi.controller.request;

import javax.validation.constraints.NotBlank;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredentialsRequest {

	@NotBlank(message = Messages.USER_LOGIN_NOT_NULL)
	private String login;
	
	@NotBlank(message = Messages.USER_PASSWORD_NOT_NULL)
	private String password;
}
