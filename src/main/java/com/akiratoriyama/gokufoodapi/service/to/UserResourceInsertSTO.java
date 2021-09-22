package com.akiratoriyama.gokufoodapi.service.to;

import java.math.BigInteger;

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
public class UserResourceInsertSTO {

	@NotNull(message = Messages.RESOURCE_ID_NOT_NULL)
	private BigInteger resourceId;
	
	@NotNull(message = Messages.USER_ID_NOT_NULL)
	private BigInteger userId;
}
