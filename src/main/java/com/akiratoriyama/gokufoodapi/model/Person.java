package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.model.converter.PersonLegalTypeConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@SuperBuilder
public class Person extends TsActiveBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="pesnid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name = "pescfirstname", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String firstName;
	
	@Column(name = "pesclastname", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String lastName;
	
	@Column(name = "pesndomlegaltype", nullable = false, columnDefinition = Const.FieldType.BIGINT )
	@Convert(converter = PersonLegalTypeConverter.class)
	private PersonLegalType legalType;
	
	@Column(name = "pesbuser", nullable = false )
	private Boolean user;

	@Override
	public Object getKeyValue() {
		return id;
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Person(BigInteger id) {
		this.id = id;
	}
}
