package com.akiratoriyama.gokufoodapi.service.to;

import javax.validation.constraints.NotBlank;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryInsertSTO {

	@NotBlank(message = Messages.COUNTRY_NAME_NOT_NULL)
	private String name;
	
	private String isoCode;
}
