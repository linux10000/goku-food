package com.akiratoriyama.gokufoodapi.infra.h2.function;

import java.util.List;
import java.util.Optional;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class H2StringRegexFunction implements SQLFunction {
	private static final String SIZE_ERRO_MSG = "H2 String Regex: At least 2 parameters must be informed";
	
	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		return false;
	}

	@Override
	public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
		return new BooleanType();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public String render(Type firstArgumentType, List args, SessionFactoryImplementor factory)
			throws QueryException {
		if ( args.size() < 2)
			throw new IllegalArgumentException(SIZE_ERRO_MSG);

		
		String field = (String) args.get(0);
		String value = (String) args.get(1);
		return field + "::text ~* " + Optional.ofNullable(value).orElse("");
	}

}
