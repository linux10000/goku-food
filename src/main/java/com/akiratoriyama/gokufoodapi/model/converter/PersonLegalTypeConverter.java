package com.akiratoriyama.gokufoodapi.model.converter;

import java.math.BigInteger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;

@Converter(autoApply = true)
public class PersonLegalTypeConverter implements AttributeConverter<PersonLegalType, BigInteger> {

	@Override
	public BigInteger convertToDatabaseColumn(PersonLegalType attribute) {
		return attribute != null ? attribute.getId() : null;
	}

	@Override
	public PersonLegalType convertToEntityAttribute(BigInteger dbData) {
		return PersonLegalType.getFromId(dbData).orElse(PersonLegalType.NOT_AVAILIBLE) ;
	}

}
