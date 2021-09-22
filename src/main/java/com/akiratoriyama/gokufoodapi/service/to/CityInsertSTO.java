package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityInsertSTO {

	@NotBlank(message = Messages.CITY_NAME_NOT_NULL)
	public String name;
	
	@NotNull(message = Messages.STATE_ID_NOT_NULL)
	private BigInteger stateId;
}
