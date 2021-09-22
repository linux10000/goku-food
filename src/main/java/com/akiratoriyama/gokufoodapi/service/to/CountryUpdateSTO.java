package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class CountryUpdateSTO extends BaseServiceSTO {

	@NotNull(message = Messages.COUNTRY_ID_NOT_NULL)
	private BigInteger id;

	@NotBlank(message = Messages.COUNTRY_NAME_NOT_NULL)
	private String name;
	
	private String isoCode;

}
