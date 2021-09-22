package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.akiratoriyama.gokufoodapi.enums.AddressType;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.model.converter.AddressTypeConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="person_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@SuperBuilder
public class PersonAddress extends TsActiveBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="pednid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name = "pedcline1", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String line1;
	
	@Column(name = "pedcline2", length = Const.FieldLength.DEFAULT_STRING, nullable = true )
	private String line2;
	
	@Column(name = "pedczip", length = Const.FieldLength.SMALL_STRING, nullable = true )
	private String zipCode;
	
	@Column(name = "pedcneighborhood", length = Const.FieldLength.DEFAULT_STRING, nullable = true )
	private String neighborhood;
	
	@Column(name = "pedndomtype", nullable = false, columnDefinition = Const.FieldType.BIGINT )
	@Convert(converter = AddressTypeConverter.class)
	private AddressType type;
	
	@Column(name ="pednpes", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = false, updatable = false)
	private BigInteger personId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="pednpes", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = true, updatable = true)
	private Person person;
	
	@Column(name ="pedccity", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = false, updatable = false)
	private BigInteger cityId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="pedccity", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = true, updatable = true)
	private City city;

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
}
