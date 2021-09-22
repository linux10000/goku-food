package com.akiratoriyama.gokufoodapi.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name="domain")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Domain extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name ="domnid", unique=true, nullable=false, columnDefinition = Const.FieldType.BIGINT)
	private BigInteger id;
	
	@Column(name = "domctable", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String table;
	
	@Column(name = "domcfield", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String field;
	
	@Column(name = "domcvalue", length = Const.FieldLength.DEFAULT_STRING, nullable = false )
	private String value;
	
	@Column(name = "domnorder", nullable = false, columnDefinition = Const.FieldType.SMALLINT )
	private Integer order;

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
