package com.cso.sikoling.abstraction.entity;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class QueryParamFilters implements Serializable {
    
    private boolean is_paging;
    private Paging paging;
    private List<Filter> fields_filter;
    private List<SortOrder> fields_sorter;

    public QueryParamFilters(boolean is_paging, Paging paging, List<Filter> fields_filter, List<SortOrder> fields_sorter) {
        this.is_paging = is_paging;
        this.paging = paging;
        this.fields_filter = fields_filter;
        this.fields_sorter = fields_sorter;
    }

    public boolean isIs_paging() {
        return is_paging;
    }

    public void setIs_paging(boolean is_paging) {
        this.is_paging = is_paging;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Filter> getFields_filter() {
        return fields_filter;
    }

    public void setFields_filter(List<Filter> fields_filter) {
        this.fields_filter = fields_filter;
    }

    public List<SortOrder> getFields_sorter() {
        return fields_sorter;
    }

    public void setFields_sorter(List<SortOrder> fields_sorter) {
        this.fields_sorter = fields_sorter;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 737;
        hash = 171 * hash + Objects.hashCode(this.is_paging);
        hash = 171 * hash + Objects.hashCode(this.paging);
        hash = 171 * hash + Objects.hashCode(this.fields_filter);
        hash = 171 * hash + Objects.hashCode(this.fields_sorter);
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
        
        return getClass() == obj.getClass();
    }
    
}