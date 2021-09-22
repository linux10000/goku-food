package com.akiratoriyama.gokufoodapi.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.akiratoriyama.gokufoodapi.infra.security.TokenSession;

public interface TokenCachedService {

	TokenSession getByRawToken(@NotBlank String rawToken);

	void insert(@NotNull TokenSession tr);

	public void deleteByRawToken(@NotBlank String rawToken);
}
