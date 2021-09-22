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
public class CityUpdateSTO extends BaseServiceSTO {
	
	@NotNull(message = Messages.CITY_ID_NOT_NULL)
	private BigInteger id;

	@NotBlank(message = Messages.CITY_ID_NOT_NULL)
	public String name;
	
	@NotNull(message = Messages.STATE_ID_NOT_NULL)
	private BigInteger stateId;
}
