package com.akiratoriyama.gokufoodapi.infra.exception;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class AdminDisableForbiddenException extends GokuException {
	private static final long serialVersionUID = 1L;

	public AdminDisableForbiddenException() {
		super(Messages.USER_ADMIN_DISABLE_NOT_SUPPORTED);
	}

}
