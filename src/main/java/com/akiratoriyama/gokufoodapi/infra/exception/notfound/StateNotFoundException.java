package com.akiratoriyama.gokufoodapi.infra.exception.notfound;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;

public class StateNotFoundException extends GokuException {
	private static final long serialVersionUID = 1L;

	public StateNotFoundException() {
		super(Messages.STATE_NOT_FOUND);
	}
}
