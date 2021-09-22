package com.akiratoriyama.gokufoodapi.repository.filter;

import java.math.BigInteger;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Validated
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonAddressFilter extends BaseFilter {

	private BigInteger id;
	private BigInteger cityId;
	private BigInteger personId;
}
