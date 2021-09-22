package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class PersonAddressNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public PersonAddressNotFoundException() {
		super(Messages.PERSONADDRESS_NOT_FOUND);
	}
}
