package com.akiratoriyama.gokufoodapi.infra.util;

public class Const {

	private Const() {
		throw new UnsupportedOperationException();
	}
	
	public static class FieldLength {
		private FieldLength() {
			throw new UnsupportedOperationException();
		}
		
		public static final int DEFAULT_STRING = 250; 
		public static final int SMALL_STRING = 50; 
	}
	
	public static class FieldType {
		private FieldType() {
			throw new UnsupportedOperationException();
		}
		
		public static final String BIGINT = "bigint"; 
		public static final String SMALLINT = "smallint"; 
		public static final String TIMESTAMP = "timestamp"; 
	}
	
	public static class Misc {
		private Misc() {
			throw new UnsupportedOperationException();
		}
		
		public static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir"); 
		public static final String OPENAPI_AUTH = "bearerAuth"; 
	}
	
	public static class Bean {
		private Bean() {
			throw new UnsupportedOperationException();
		}
		
		public static final String AUDITING_DATETIME_PROVIDER = "auditingDateTimeProvider"; 
	}
	
	public static class Cache {
		private Cache() {
			throw new UnsupportedOperationException();
		}
		
		public static final String ZIP_CODE = "zip_code";
		public static final String TOKEN = "token";
	}
	
	public static class Resources {
		private Resources() {
			throw new UnsupportedOperationException();
		}
		
		public static final String USER_FILTER = "10000";
		public static final String USER_ENABLE = "10001";
		public static final String USER_DISABLE = "10002";
		
		public static final String COUNTRY_FILTER = "20000";
		public static final String COUNTRY_INSERT = "20001";
		public static final String COUNTRY_UPDATE = "20002";
		public static final String COUNTRY_DELETE = "20003";
		
		public static final String ADDRESS_FILTER = "30000";
		public static final String ADDRESS_INSERT = "30001";
		public static final String ADDRESS_UPDATE = "30002";
		public static final String ADDRESS_DELETE = "30003";
		public static final String ADDRESS_FILTER_BY_ZIPCODE = "30004";
		
		public static final String CITY_FILTER = "40000";
		public static final String CITY_INSERT = "40001";
		public static final String CITY_UPDATE = "40002";
		public static final String CITY_DELETE = "40003";
		
		public static final String PERSONADDRESS_FILTER = "50000";
		public static final String PERSONADDRESS_INSERT = "50001";
		public static final String PERSONADDRESS_UPDATE = "50002";
		public static final String PERSONADDRESS_DELETE = "50003";
		
		public static final String PERSON_FILTER = "60000";
		public static final String PERSON_INSERT = "60001";
		public static final String PERSON_UPDATE = "60002";
		public static final String PERSON_DELETE = "60003";
		
		public static final String STATE_FILTER = "70000";
		public static final String STATE_INSERT = "70001";
		public static final String STATE_UPDATE = "70002";
		public static final String STATE_DELETE = "70003";
		
		public static final String USERRESOURCE_FILTER = "80000";
		public static final String USERRESOURCE_INSERT = "80001";
		public static final String USERRESOURCE_DELETE = "80002";

		public static final String RESOURCE_FILTER = "90000";
		public static final String RESOURCE_FILTER_NOT_CONTAINED_IN_USER = "90001";
	}
}
