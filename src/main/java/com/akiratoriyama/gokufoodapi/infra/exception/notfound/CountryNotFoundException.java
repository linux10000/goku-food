package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class CountryNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public CountryNotFoundException() {
		super(Messages.COUNTRY_NOT_FOUND);
	}
}
