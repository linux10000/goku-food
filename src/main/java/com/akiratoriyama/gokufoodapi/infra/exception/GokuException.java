package com.akiratoriyama.gokufoodapi.infra.exception;

public class GokuException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Object[] args;
	
	public GokuException(String msg) {
		super(msg);
	}
	
	public GokuException(Throwable ex) {
		super(ex);
	}
	
	public GokuException(String msg, Object...args) {
		super(msg);
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}
}
