package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class UserResourceNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public UserResourceNotFoundException() {
		super(Messages.USERRESOURCE_NOT_FOUND);
	}
}
