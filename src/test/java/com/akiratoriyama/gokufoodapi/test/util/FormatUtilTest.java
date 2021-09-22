package com.akiratoriyama.gokufoodapi.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.akiratoriyama.gokufoodapi.infra.util.FormatUtil;

class FormatUtilTest {

	@Test
	void testGetOnlyNumbersAndLetters() {
		assertEquals("11070020", FormatUtil.getOnlyNumbersAndLetters("11070-020 ./+-#$%&*="));
	}
}
