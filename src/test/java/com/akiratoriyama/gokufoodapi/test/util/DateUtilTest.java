package com.akiratoriyama.gokufoodapi.test.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import com.akiratoriyama.gokufoodapi.infra.util.DateUtil;

class DateUtilTest {

	@Test
	void testOffsetDateTimeToString() {
		assertEquals("2000-01-01T00:00:00.000000+0000", DateUtil.offsetDateTimeToString(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)));
	}

	@Test
	void testStringToOffsetDateTime() {
		assertEquals(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), DateUtil.stringToOffsetDateTime("2000-01-01T00:00:00.000000+0000"));
	}

	@Test
	void testLocalDateToString() {
		assertEquals("2000-01-01", DateUtil.localDateToString(LocalDate.of(2000, 1, 1)));
	}

	@Test
	void testStringToLocalDate() {
		assertEquals(LocalDate.of(2000, 1, 1), DateUtil.stringToLocalDate("2000-01-01"));
	}

	@Test
	void testLocalDateTimeToString() {
		assertEquals("2000-01-01T00:00:00.000000", DateUtil.localDateTimeToString(LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0)));
	}

	@Test
	void testStringToLocalDateTime() {
		assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0), DateUtil.stringToLocalDateTime("2000-01-01T00:00:00.000000"));
	}
}
