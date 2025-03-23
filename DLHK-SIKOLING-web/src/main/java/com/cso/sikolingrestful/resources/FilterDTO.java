package com.cso.sikolingrestful.resources;

import com.cso.sikoling.abstraction.entity.Filter;
import java.util.Objects;

public class FilterDTO {

    private String field_name;
    private String value;

    public FilterDTO() {
    }

    public FilterDTO(Filter t) {
        if(t != null) {
            this.field_name = t.getField_name();
            this.value = t.getValue();
        }
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public Filter toFilter() {
        return new Filter(field_name, value);
    }
    
    @Override
    public int hashCode() {
        int hash = 1113;
        hash = 171 * hash + Objects.hashCode(this.field_name);
        hash = 171 * hash + Objects.hashCode(this.value);
        
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
        
        final FilterDTO other = (FilterDTO) obj;
        
        if (!this.field_name.equalsIgnoreCase(other.getField_name())) {
            return false;
        }  
        
        return this.value.equalsIgnoreCase(other.getValue());
    }
	
    @Override
    public String toString() {
        return "FilterDTO{fieldNama="				
                .concat(this.field_name)
                .concat(", value=")
                .concat(this.value)
                .concat("}");	  
}

}
