package com.akiratoriyama.gokufoodapi.controller.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.akiratoriyama.gokufoodapi.model.City;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonAddressResponse {

	private BigInteger id;
	private String line1;
	private String line2;
	private String neighborhood;
	private String zipCode;
	private EnumResponse type;
	private City city;
	private PersonResponse person;
	private LocalDateTime ts;
}
