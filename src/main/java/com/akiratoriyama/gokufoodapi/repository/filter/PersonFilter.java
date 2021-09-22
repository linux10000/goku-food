package com.akiratoriyama.gokufoodapi.repository.filter;

import java.math.BigInteger;

import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;

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
public class PersonFilter extends BaseFilter {

	private BigInteger id;
	private Boolean user;
	private PersonLegalType legalType;
}
