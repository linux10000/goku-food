package com.akiratoriyama.gokufoodapi.controller.response;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilterResponse {

	private BigInteger id;
	private String login;
	private String firstName;
	private String lastName;
	private LocalDateTime ts;
	private EnumResponse legalType;
}
