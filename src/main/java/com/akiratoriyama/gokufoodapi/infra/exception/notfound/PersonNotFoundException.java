package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class PersonNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException() {
		super(Messages.PERSON_NOT_FOUND);
	}
}
