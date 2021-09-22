package com.akiratoriyama.gokufoodapi.service.to;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseServiceSTO {

	@NotNull(message = Messages.TS_NOT_NULL)
	private LocalDateTime ts;
}
