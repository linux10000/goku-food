package com.akiratoriyama.gokufoodapi.infra.util;

import java.util.Objects;

public class FormatUtil {
	private static final String ONLY_NUMBERS_AND_LETTERS = "[^a-zA-Z0-9]*";
	public static final String EMPTY =  "";
	
	private FormatUtil() {
		throw new UnsupportedOperationException();
	}

    public static String getOnlyNumbersAndLetters(String txt) {
        return Objects.requireNonNullElse(txt, EMPTY).replaceAll(ONLY_NUMBERS_AND_LETTERS, EMPTY);
    }
}
