package com.akiratoriyama.gokufoodapi.model;

import java.io.Serializable;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonIgnore
    @Transient
    public abstract Object getKeyValue();

    @Override
    public boolean equals(Object other) {
        return other instanceof BaseModel && (this.getKeyValue() != null) ? this.getKeyValue().equals(((BaseModel) other).getKeyValue()) : (other == this);
    }

    @Override
    public int hashCode() {
        return getKeyValue() != null ? this.getClass().hashCode() + getKeyValue().hashCode() : super.hashCode();
    }
}
