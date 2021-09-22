package com.akiratoriyama.gokufoodapi.infra.util;

public class Messages {
	
	private Messages() {
		throw new UnsupportedOperationException();
	}

	public static final String USER_NOT_FOUND = "user.not.found";
	public static final String USER_ID_NOT_NULL = "user.id.not.null";
	public static final String USER_LOGIN_NOT_NULL = "user.login.not.null";
	public static final String USER_LOGIN_SIZE_NOT_VALID = "user.login.size.not.valid";
	public static final String USER_PASSWORD_NOT_NULL = "user.password.not.null";
	public static final String USER_PASSWORD_SIZE_NOT_VALID = "user.password.size.not.valid";
	public static final String USER_NEWPASSWORD_NOT_NULL = "user.newpassword.not.null";
	public static final String USER_FIRSTNAME_NOT_NULL = "user.firstname.not.null";
	public static final String USER_FIRSTNAME_SIZE_NOT_VALID = "user.firstname.size.not.valid";
	public static final String USER_LASTNAME_NOT_NULL = "user.lastname.not.null";
	public static final String USER_LASTNAME_SIZE_NOT_VALID = "user.lastname.size.not.valid";
	public static final String USER_LEGALTYPE_NOT_NULL = "user.legaltype.not.null";
	public static final String USER_ADMIN_DISABLE_NOT_SUPPORTED = "user.admin.disable.not.supported";
	
	public static final String COUNTRY_NOT_FOUND = "country.not.found";
	public static final String COUNTRY_ID_NOT_NULL = "country.id.not.null";
	public static final String COUNTRY_NAME_NOT_NULL = "country.name.not.null";
	
	public static final String STATE_NOT_FOUND = "state.not.found";
	public static final String STATE_ID_NOT_NULL = "state.id.not.null";
	public static final String STATE_NAME_NOT_NULL = "state.name.not.null";
	
	public static final String CITY_NOT_FOUND = "city.not.found";
	public static final String CITY_ID_NOT_NULL = "city.id.not.null";
	public static final String CITY_NAME_NOT_NULL = "city.name.not.null";
	
	public static final String ADDRESS_NOT_FOUND = "address.not.found";
	public static final String ADDRESS_ID_NOT_NULL = "address.id.not.null";
	public static final String ADDRESS_LINE1_NOT_NULL = "address.line1.not.null";
	public static final String ADDRESS_ZIPCODE_NOT_NULL = "address.zipcode.not.null";

	public static final String PERSON_NOT_FOUND = "person.not.found";
	public static final String PERSON_ID_NOT_NULL = "person.id.not.null";
	public static final String PERSON_FIRTNAME_NOT_NULL = "person.firstname.not.null";
	public static final String PERSON_LASTNAME_NOT_NULL = "person.lastname.not.null";
	public static final String PERSON_LEGALTYPE_NOT_NULL = "person.legaltype.not.null";
	
	public static final String PERSONADDRESS_NOT_FOUND = "personaddress.not.found";
	public static final String PERSONADDRESS_ID_NOT_NULL = "personaddress.id.not.null";
	public static final String PERSONADDRESS_LINE1_NOT_NULL = "personaddress.line1.not.null";
	public static final String PERSONADDRESS_TYPE_NOT_NULL = "personaddress.type.not.null";

	public static final String RESOURCE_ID_NOT_NULL = "resource.id.not.null";

	public static final String USERRESOURCE_NOT_FOUND = "userresource.not.found";
	public static final String USERRESOURCE_ID_NOT_NULL = "userresource.id.not.null";
	public static final String USERRESOURCE_UNIQUE_VIOLATION = "userresource.unique.violation";

	public static final String CREDENTIALS_NOT_VALID = "credentials.not.valid";
	
	public static final String TS_NOT_NULL = "ts.not.null";
	public static final String REFRESHTOKEN_NOT_NULL = "refreshtoken.not.null";
}
