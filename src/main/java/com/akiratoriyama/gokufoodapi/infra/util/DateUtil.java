package com.akiratoriyama.gokufoodapi.infra.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	
	public static final String DEFAULT_TIMEZONE = "GMT";
	public static final String OFFSETDATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";
	public static final String LOCALDATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
	public static final String LOCALDATE_FORMAT = "yyyy-MM-dd";
	
	private DateUtil() {
		throw new UnsupportedOperationException();
	}

	public static String offsetDateTimeToString(OffsetDateTime obj){
		if ( obj == null )
			return null;
		
		return obj.format(DateTimeFormatter.ofPattern(OFFSETDATETIME_FORMAT));
	}
	
	public static OffsetDateTime stringToOffsetDateTime(String obj){
		if ( StringUtils.isBlank(obj) )
			return null;
		
		return OffsetDateTime.parse(obj, DateTimeFormatter.ofPattern(OFFSETDATETIME_FORMAT));
	}
	
	public static String localDateToString(LocalDate obj){
		if ( obj == null )
			return null;
		
		return obj.format(DateTimeFormatter.ofPattern(LOCALDATE_FORMAT));
	}
	
	public static LocalDate stringToLocalDate(String obj){
		if ( StringUtils.isBlank(obj) )
			return null;
		
		return LocalDate.parse(obj, DateTimeFormatter.ofPattern(LOCALDATE_FORMAT));
	}
	
	public static LocalDateTime stringToLocalDateTime(String obj){
		if ( StringUtils.isBlank(obj) )
			return null;
		
		return LocalDateTime.parse(obj, DateTimeFormatter.ofPattern(LOCALDATETIME_FORMAT));
	}
	
	public static String localDateTimeToString(LocalDateTime obj) {
		if ( obj == null )
			return null;
		
		return obj.format(DateTimeFormatter.ofPattern(LOCALDATETIME_FORMAT));
	}
	
	public static LocalDateTime from(Timestamp ts) {
		if ( ts == null )
			return null;
		
		return ts.toLocalDateTime();
	}
	
	public static Timestamp from(LocalDateTime ts) {
		if ( ts == null )
			return null;
		
		return Timestamp.valueOf(ts);
	}
}
