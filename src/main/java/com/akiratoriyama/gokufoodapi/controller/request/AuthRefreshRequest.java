package com.akiratoriyama.gokufoodapi.controller.request;

import javax.validation.constraints.NotBlank;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshRequest {

	@NotBlank(message = Messages.REFRESHTOKEN_NOT_NULL)
	private String refreshToken;
}
