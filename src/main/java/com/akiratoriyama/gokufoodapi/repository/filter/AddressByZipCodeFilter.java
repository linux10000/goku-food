package com.akiratoriyama.gokufoodapi.repository.filter;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

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
public class AddressByZipCodeFilter extends BaseFilter {

	@NotBlank(message = Messages.ADDRESS_ZIPCODE_NOT_NULL)
	private String zipCode;
	
	private boolean ignoreSpecialChars;
}
