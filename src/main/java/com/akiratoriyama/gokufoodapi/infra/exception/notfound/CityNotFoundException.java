package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class CityNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public CityNotFoundException() {
		super(Messages.CITY_NOT_FOUND);
	}
}
