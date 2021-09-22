package com.akiratoriyama.gokufoodapi.infra.exception;

public class GokuRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Object[] args;
	
	public GokuRuntimeException(String msg) {
		super(msg);
	}
	
	public GokuRuntimeException(Throwable ex) {
		super(ex);
	}
	
	public GokuRuntimeException(String msg, Object...args) {
		super(msg);
		this.args = args;
	}

	public Object[] getArgs() {
		return args;
	}
}
