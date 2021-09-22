package com.akiratoriyama.gokufoodapi.infra.exception;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class InvalidCredentialsException extends GokuException {
	private static final long serialVersionUID = 6242420927813845671L;

	public InvalidCredentialsException() {
		super(Messages.CREDENTIALS_NOT_VALID);
	}
}
