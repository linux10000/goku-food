package com.akiratoriyama.gokufoodapi.infra.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class CryptUtils {
	
	private static final String SHA256_SALT = "5E=8P/HJt7Zhq^h-";
	
	private CryptUtils() {
		throw new UnsupportedOperationException();
	}

	public static String stringToSHA256(String value) {
		return Hashing.sha256().hashString(value + SHA256_SALT, StandardCharsets.UTF_8).toString();
	}
	
	public static String stringToSHA256(String value, String salt) {
		return Hashing.sha256().hashString(value + salt, StandardCharsets.UTF_8).toString();
	}
}
