package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;

public class SortOrder implements Serializable {
    
    private final String field_name;
    private final String value;

    public SortOrder(String fieldName, String value) {
            this.field_name = fieldName;
            this.value = value;
    }

    public String getField_name() {
            return field_name;
    }

    public String getValue() {
            return value;
    }

    @Override
    public int hashCode() {
            int hash = 171;
            hash = 151 * hash + Objects.hashCode(field_name);
            hash = 151 * hash + Objects.hashCode(value);
            return hash;
    }

    @Override
    public boolean equals(Object obj) {
            if (this == obj) {
        return true;
    }

    if (obj == null) {
        return false;
    }

    if (getClass() != obj.getClass()) {
        return false;
    }

    final SortOrder other = (SortOrder) obj;

    if ( !this.field_name.equals(other.getField_name()) ) {
        return false;
    }

    return true;
    }

    @Override
    public String toString() {
            return "{SortOrder{ fieldName:"
                            .concat(field_name)
                            .concat(", value:")
                            .concat(value)
                            .concat("}");
    }
}