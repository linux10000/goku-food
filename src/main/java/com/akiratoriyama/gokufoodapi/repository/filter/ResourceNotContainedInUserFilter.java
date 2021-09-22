package com.akiratoriyama.gokufoodapi.repository.filter;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

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
public class ResourceNotContainedInUserFilter extends BaseFilter {

	private BigInteger id;

	@NotNull(message = Messages.USER_ID_NOT_NULL)
	private BigInteger userId;
	
}
