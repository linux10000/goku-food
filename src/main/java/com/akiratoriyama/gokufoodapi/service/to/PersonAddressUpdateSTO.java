package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.enums.AddressType;
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
public class PersonAddressUpdateSTO extends BaseServiceSTO {
	
	@NotNull(message = Messages.PERSONADDRESS_ID_NOT_NULL)
	private BigInteger id;

	@NotBlank(message = Messages.PERSONADDRESS_LINE1_NOT_NULL)
	private String line1;
	
	@NotNull(message = Messages.CITY_ID_NOT_NULL)
	private BigInteger cityId;
	
	@NotNull(message = Messages.PERSONADDRESS_TYPE_NOT_NULL)
	private AddressType type;
	
	private String line2;
	private String neighborhood;
	private String zipCode;
}
