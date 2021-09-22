package com.akiratoriyama.gokufoodapi.infra.exception;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class UserResourceUniqueViolationException extends GokuException {
	private static final long serialVersionUID = 1L;

	public UserResourceUniqueViolationException() {
		super(Messages.USERRESOURCE_UNIQUE_VIOLATION);
	}
}
