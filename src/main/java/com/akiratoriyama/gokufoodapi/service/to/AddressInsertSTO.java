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
public class AddressInsertSTO {

	@NotBlank(message = Messages.ADDRESS_LINE1_NOT_NULL)
	private String line1;
	
	@NotNull(message = Messages.CITY_ID_NOT_NULL)
	private BigInteger cityId;
	
	private String line2;
	private String neighborhood;
	private String zipCode;
}
