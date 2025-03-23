package com.cso.sikolingrestful.resources;

import com.cso.sikoling.abstraction.entity.Paging;
import java.util.Objects;

public class PagingDTO {

    private Integer page_number;
    private Integer page_size;
    
    public PagingDTO() {
    }

    public PagingDTO(Paging t) {
        if(t != null) {
            this.page_number = t.getPage_number();
            this.page_size = t.getPage_size();
        }
    }

    public Integer getPage_number() {
        return page_number;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
    
    public Paging toPaging() {
        return new Paging(this.page_number, this.page_size);
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
