package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;


public class Paging implements Serializable {
    
    private final Integer page_number;
    private final Integer page_size;

    public Paging(Integer page_number, Integer page_size) {
        this.page_number = page_number;
        this.page_size = page_size;
    }

    public Integer getPage_number() {
        return page_number;
    }

    public Integer getPage_size() {
        return page_size;
    }
    
    @Override
    public int hashCode() {
            int hash = 71;
            hash = 141 * hash + Objects.hashCode(page_number);
            hash = 141 * hash + Objects.hashCode(page_size);
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

        final Paging other = (Paging) obj;
        
        if ( !this.page_number.equals(other.getPage_number()) ) {
            return false;
        }

        return this.page_size.equals(other.getPage_size());
    }
    
    @Override
    public String toString() {
        return "{Paging{ page_number:"
                .concat(page_number.toString())
                .concat(", page_size:")
                .concat(page_size.toString())
                .concat("}");
    }
}
