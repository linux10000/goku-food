package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class AddressNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public AddressNotFoundException() {
		super(Messages.ADDRESS_NOT_FOUND);
	}
}
