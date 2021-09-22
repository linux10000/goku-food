package com.akiratoriyama.gokufoodapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public abstract class TsActiveBaseModel extends BaseModel {
	private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "created_at", nullable = false )
    private LocalDateTime created;
	
    //TODO implementar controle de concorrencia
    @LastModifiedDate
	@Column(name = "ts", nullable = false, columnDefinition = Const.FieldType.TIMESTAMP )
    @JsonProperty("ts")
	private LocalDateTime ts;
	
	@Column(name = "active", nullable = false )
	private Boolean active;
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
