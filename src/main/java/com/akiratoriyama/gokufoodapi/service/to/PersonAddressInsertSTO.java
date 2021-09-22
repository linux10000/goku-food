package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.enums.AddressType;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonAddressInsertSTO {

	@NotBlank(message = Messages.PERSONADDRESS_LINE1_NOT_NULL)
	private String line1;
	
	@NotNull(message = Messages.CITY_ID_NOT_NULL)
	private BigInteger cityId;
	
	@NotNull(message = Messages.PERSON_ID_NOT_NULL)
	private BigInteger personId;
	
	@NotNull(message = Messages.PERSONADDRESS_TYPE_NOT_NULL)
	private AddressType type;
	
	private String line2;
	private String neighborhood;
	private String zipCode;
}
