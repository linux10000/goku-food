package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@SuperBuilder
public class User extends TsActiveBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name ="pesnid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name = "usrclogin", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String login;
	
	@Column(name = "usrcpassword", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String password;
	
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name ="pesnid", referencedColumnName = "pesnid", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = false, updatable = false)
	private Person person;

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

	public User(BigInteger id) {
		this.id = id;
	}
}
