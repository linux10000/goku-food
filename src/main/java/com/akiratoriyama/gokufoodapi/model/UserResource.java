package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="user_resource")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@SuperBuilder
public class UserResource extends TsActiveBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ucenid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name ="ucenresource", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = false, updatable = false)
	private BigInteger resourceId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="ucenresource", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = true, updatable = true)
	private Resource resource;
	
	@Column(name ="ucenperson", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = false, updatable = false)
	private BigInteger userId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="ucenperson", nullable=false, columnDefinition = Const.FieldType.BIGINT, insertable = true, updatable = true)
	private User user;

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
