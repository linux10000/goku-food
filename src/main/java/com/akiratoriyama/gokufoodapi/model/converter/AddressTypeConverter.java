package com.akiratoriyama.gokufoodapi.model.converter;

import java.math.BigInteger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.akiratoriyama.gokufoodapi.enums.AddressType;

@Converter(autoApply = true)
public class AddressTypeConverter implements AttributeConverter<AddressType, BigInteger> {

	@Override
	public BigInteger convertToDatabaseColumn(AddressType attribute) {
		return attribute != null ? attribute.getId() : null;
	}

	@Override
	public AddressType convertToEntityAttribute(BigInteger dbData) {
		return AddressType.getFromId(dbData).orElse(AddressType.NOT_AVAILIBLE) ;
	}

}
