package com.akiratoriyama.gokufoodapi.service.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordSTO {

	@NotBlank(message = Messages.USER_LOGIN_NOT_NULL)
	private String authenticatedUser;
	
	@NotBlank(message = Messages.USER_LOGIN_NOT_NULL)
	private String login;
	
	@NotBlank(message = Messages.USER_PASSWORD_NOT_NULL)
	private String oldPassword;
	
	@NotBlank(message = Messages.USER_NEWPASSWORD_NOT_NULL)
	@Size(min = 8, max = 250, message = Messages.USER_PASSWORD_SIZE_NOT_VALID)
	private String newPassword;
}
