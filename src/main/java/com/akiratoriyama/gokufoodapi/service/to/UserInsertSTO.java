package com.akiratoriyama.gokufoodapi.service.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertSTO {

	@NotBlank(message = Messages.USER_LOGIN_NOT_NULL)
	@Size(min = 3, max = 250, message = Messages.USER_LOGIN_SIZE_NOT_VALID)
	private String login;
	
	@NotBlank(message = Messages.USER_PASSWORD_NOT_NULL)
	@Size(min = 8, max = 250, message = Messages.USER_PASSWORD_SIZE_NOT_VALID)
	private String password;
	
	@NotBlank(message = Messages.USER_FIRSTNAME_NOT_NULL)
	@Size(min = 3, max = 250, message = Messages.USER_FIRSTNAME_SIZE_NOT_VALID)
	private String firstName;
	
	@NotBlank(message = Messages.USER_LASTNAME_NOT_NULL)
	@Size(min = 3, max = 250, message = Messages.USER_LASTNAME_SIZE_NOT_VALID)
	private String lastName;
	
	@NotNull(message = Messages.USER_LEGALTYPE_NOT_NULL)
	private PersonLegalType legalType;
}
