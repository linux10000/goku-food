package com.akiratoriyama.gokufoodapi.service.to;

import javax.validation.constraints.NotBlank;

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
public class PersonInsertSTO {

	@NotBlank(message = Messages.PERSON_FIRTNAME_NOT_NULL)
	private String firstName;
	
	@NotBlank(message = Messages.PERSON_LASTNAME_NOT_NULL)
	private String lastName;
	
	@NotBlank(message = Messages.PERSON_LEGALTYPE_NOT_NULL)
	private PersonLegalType legalType;
	
	private boolean user;
}
