package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateSTO extends BaseServiceSTO {
	
	@NotNull(message = Messages.PERSON_ID_NOT_NULL)
	private BigInteger id;

	@NotBlank(message = Messages.PERSON_FIRTNAME_NOT_NULL)
	private String firstName;
	
	@NotBlank(message = Messages.PERSON_LASTNAME_NOT_NULL)
	private String lastName;
	
	@NotBlank(message = Messages.PERSON_LEGALTYPE_NOT_NULL)
	private PersonLegalType legalType;
}
