package com.akiratoriyama.gokufoodapi.infra.security;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String exception;
}
