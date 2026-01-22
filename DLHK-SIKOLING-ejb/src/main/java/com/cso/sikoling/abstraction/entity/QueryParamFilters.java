package com.cso.sikoling.abstraction.entity;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class QueryParamFilters implements Serializable {
    
    private boolean isPaging;
    private Paging paging;
    private List<Filter> daftarFieldFilter;
    private List<SortOrder> daftarFieldsSorter;

    public QueryParamFilters(boolean isPaging, Paging paging, 
            List<Filter> daftarFieldFilter, List<SortOrder> daftarFieldsSorter) {
        this.isPaging = isPaging;
        this.paging = paging;
        this.daftarFieldFilter = daftarFieldFilter;
        this.daftarFieldsSorter = daftarFieldsSorter;
    }

    public boolean isIsPaging() {
        return isPaging;
    }

    public void setIsPaging(boolean isPaging) {
        this.isPaging = isPaging;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Filter> getDaftarFieldFilter() {
        return daftarFieldFilter;
    }

    public void setDaftarFieldFilter(List<Filter> daftarFieldFilter) {
        this.daftarFieldFilter = daftarFieldFilter;
    }

    public List<SortOrder> getDaftarFieldsSorter() {
        return daftarFieldsSorter;
    }

    public void setDaftarFieldsSorter(List<SortOrder> daftarFieldsSorter) {
        this.daftarFieldsSorter = daftarFieldsSorter;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 737;
        hash = 171 * hash + Objects.hashCode(this.isPaging);
        hash = 171 * hash + Objects.hashCode(this.paging);
        hash = 171 * hash + Objects.hashCode(this.daftarFieldFilter);
        hash = 171 * hash + Objects.hashCode(this.daftarFieldsSorter);
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