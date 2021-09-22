package com.akiratoriyama.gokufoodapi.infra.util;

public class Endpoint {
	public static final String PREFIX_API = "/api";
	public static final String PREFIX_V1 = "/v1";
	public static final String PREFIX_API_V1 = PREFIX_API + PREFIX_V1;

	public static final String SUFFIX_FILTER = "/filter";
	
	private Endpoint() {
		throw new UnsupportedOperationException();
	}
	
	public static class User {
		private static final String PREFIX_USER = "/user";
		
		private User() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_USER + SUFFIX_FILTER; 
			public static final String URL_INFO = PREFIX_API_V1 + PREFIX_USER + "/info"; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_USER; 
			public static final String URL_CHANGE_PASSWORD = PREFIX_API_V1 + PREFIX_USER + "/change-password"; 
			public static final String URL_ENABLE = PREFIX_API_V1 + PREFIX_USER + "/enable"; 
			public static final String URL_DISABLE = PREFIX_API_V1 + PREFIX_USER + "/disable";
		}
	}
	
	public static class Country {
		private static final String PREFIX_COUNTRY = "/country";
		
		private Country() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_COUNTRY + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_COUNTRY; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_COUNTRY; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_COUNTRY; 
		}
	}
	
	public static class State {
		private static final String PREFIX_STATE = "/state";
		
		private State() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_STATE + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_STATE; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_STATE; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_STATE; 
		}
	}
	
	public static class City {
		private static final String PREFIX_CITY = "/city";
		
		private City() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_CITY + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_CITY; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_CITY; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_CITY; 
		}
	}

	public static class Address {
		private static final String PREFIX_ADDRESS = "/address";
		
		private Address() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_ADDRESS + SUFFIX_FILTER; 
			public static final String URL_FILTER_BY_ZIPCODE = PREFIX_API_V1 + PREFIX_ADDRESS + "/filter-by-zipcode"; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_ADDRESS; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_ADDRESS; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_ADDRESS; 
		}
	}

	public static class Person {
		private static final String PREFIX_PERSON = "/person";
		
		private Person() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_PERSON + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_PERSON; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_PERSON; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_PERSON; 
		}
	}
	
	public static class PersonAddress {
		private static final String PREFIX_PERSON_ADDRESS = "/person-address";
		
		private PersonAddress() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_PERSON_ADDRESS + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_PERSON_ADDRESS; 
			public static final String URL_UPDATE = PREFIX_API_V1 + PREFIX_PERSON_ADDRESS; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_PERSON_ADDRESS; 
		}
	}
	
	public static class UserResource {
		private static final String PREFIX_USER_RESOURCE = "/user-resource";
		
		private UserResource() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_USER_RESOURCE + SUFFIX_FILTER; 
			public static final String URL_INSERT = PREFIX_API_V1 + PREFIX_USER_RESOURCE; 
			public static final String URL_DELETE = PREFIX_API_V1 + PREFIX_USER_RESOURCE; 
		}
	}
	
	public static class Resource {
		private static final String PREFIX_USER_RESOURCE = "/resource";
		
		private Resource() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_FILTER = PREFIX_API_V1 + PREFIX_USER_RESOURCE + SUFFIX_FILTER; 
			public static final String URL_FILTER_NOT_CONTAINED_IN_USER = PREFIX_API_V1 + PREFIX_USER_RESOURCE + "/filter-not-contained-in-user"; 
		}
	}
	
	public static class Auth {
		private static final String PREFIX_AUTH = "/auth";
		
		private Auth() {
			throw new UnsupportedOperationException();
		}
		
		public static class V1 {
			private V1() {
				throw new UnsupportedOperationException();
			}
			
			public static final String URL_TOKEN = PREFIX_API_V1 + PREFIX_AUTH + "/token"; 
			public static final String URL_REFRESH_TOKEN = PREFIX_API_V1 + PREFIX_AUTH + "/refresh-token"; 
			public static final String URL_LOGOUT = PREFIX_API_V1 + PREFIX_AUTH + "/logout"; 
		}
	}
}
