package com.akiratoriyama.gokufoodapi.enums;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum PersonLegalType implements I18nEnum {
	
	PHYSICAL(BigInteger.ONE, Consts.PERSON_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "PHYSICAL"),
	JURIDICAL(BigInteger.valueOf(2), Consts.PERSON_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "PHYSICAL"),
	NOT_AVAILIBLE(BigInteger.valueOf(3), Consts.PERSON_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "NOT_AVAILIBLE");
	
	private static final class Consts {
		public static final String PERSON_TABLE = "person";
		public static final String LEGAL_TYPE_FIELD = "pesndomlegaltype";
		public static final String I18N_PREFIX = PERSON_TABLE + "." + LEGAL_TYPE_FIELD + ".";
	}

	private static final PersonLegalType[] _VALUES = values();
	
	private BigInteger id;
	private String table;
	private String field;
	private String i18nKey;

	PersonLegalType(BigInteger id, String table, String field, String i18nKey) {
		PersonLegalType.
		this.id = id;
		this.table = table;
		this.field = field;
		this.i18nKey = i18nKey;
	}
	
	public static Optional<PersonLegalType> getFromId(BigInteger id) {
		return Stream.of(_VALUES)
				.filter(v -> v.getId().equals(id))
				.findFirst();
	}

	@Override
	public String getI18nKey() {
		return this.i18nKey;
	}
}
