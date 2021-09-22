package com.akiratoriyama.gokufoodapi.infra.h2;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

import com.akiratoriyama.gokufoodapi.infra.h2.function.H2StringRegexFunction;

public class GokuH2SQLDialect extends H2Dialect {

	public GokuH2SQLDialect() {
		registerFunction("string_regex", new H2StringRegexFunction());
		registerFunction("totalRecords", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "count(*) over()"));
	}
}
