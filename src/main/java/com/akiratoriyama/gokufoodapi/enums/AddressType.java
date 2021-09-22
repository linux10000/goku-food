package com.akiratoriyama.gokufoodapi.enums;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum AddressType implements I18nEnum {
	
	DELIVERY(BigInteger.ONE, Consts.PERSONADDRESS_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "DELIVERY"),
	BILLING(BigInteger.valueOf(2), Consts.PERSONADDRESS_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "BILLING"),
	NOT_AVAILIBLE(BigInteger.valueOf(3), Consts.PERSONADDRESS_TABLE, Consts.LEGAL_TYPE_FIELD, Consts.I18N_PREFIX + "NOT_AVAILIBLE");
	
	private static final class Consts {
		public static final String PERSONADDRESS_TABLE = "personaddress";
		public static final String LEGAL_TYPE_FIELD = "pedndomtype";
		public static final String I18N_PREFIX = PERSONADDRESS_TABLE + "." + LEGAL_TYPE_FIELD + ".";
	}

	private static final AddressType[] _VALUES = values();
	
	private BigInteger id;
	private String table;
	private String field;
	private String i18nKey;

	AddressType(BigInteger id, String table, String field, String i18nKey) {
		AddressType.
		this.id = id;
		this.table = table;
		this.field = field;
		this.i18nKey = i18nKey;
	}
	
	public static Optional<AddressType> getFromId(BigInteger id) {
		return Stream.of(_VALUES)
				.filter(v -> v.getId().equals(id))
				.findFirst();
	}

	@Override
	public String getI18nKey() {
		return this.i18nKey;
	}
}
