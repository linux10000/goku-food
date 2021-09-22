package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@SuperBuilder
public class Country extends TsActiveBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="counid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name = "coucname", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String name;
	
	@Column(name = "coucisocode", length = Const.FieldLength.SMALL_STRING, nullable = true )
	private String isoCode;

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

	public Country(BigInteger id) {
		this.id = id;
	}
}
