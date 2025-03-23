package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;

public class Filter implements Serializable {
    private final String field_name;
    private final String value;

    public Filter(String fieldName, String value) {
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
            int hash = 131;
            hash = 141 * hash + Objects.hashCode(field_name);
            hash = 141 * hash + Objects.hashCode(value);
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

        final Filter other = (Filter) obj;

        if ( !this.field_name.equals(other.getField_name()) ) {
            return false;
        }

        return this.value.equals(other.getValue());
    }

    @Override
    public String toString() {
        return "{Filter{ fieldName:"
                .concat(field_name)
                .concat(", value:")
                .concat(value)
                .concat("}");
    }
}