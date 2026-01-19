package com.cso.sikolingrestful.resources;

import java.util.List;
import java.util.stream.Collectors;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import java.util.Objects;

public class QueryParamFiltersDTO {    
    private boolean is_paging;
    private PagingDTO paging;
    private List<FilterDTO> fields_filter;
    private List<SortOrderDTO> fields_sorter;
    
    public QueryParamFiltersDTO() { }
	
    public QueryParamFiltersDTO(QueryParamFilters t) {
        if(t != null) {
            this.is_paging = t.isIs_paging();
            this.paging = t.getPaging() != null ? new PagingDTO(t.getPaging()) : null;
            this.fields_filter = t.getFields_filter() != null ?
                            t.getFields_filter()
                            .stream()
                            .map(i -> new FilterDTO(i))
                            .collect(Collectors.toList()) : null;
            this.fields_sorter = t.getFields_sorter()!= null ?
                            t.getFields_sorter()
                            .stream()
                            .map(i -> new SortOrderDTO(i))
                            .collect(Collectors.toList()) : null;
        }
    }

    public boolean getIs_paging() {
        return is_paging;
    }

    public void setIs_paging(boolean is_paging) {
        this.is_paging = is_paging;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public void setPaging(PagingDTO paging) {
        this.paging = paging;
    }

    public List<FilterDTO> getFields_filter() {
        return fields_filter;
    }

    public void setFields_filter(List<FilterDTO> fields_filter) {
        this.fields_filter = fields_filter;
    }

    public List<SortOrderDTO> getFields_sorter() {
        return fields_sorter;
    }

    public void setFields_sorter(List<SortOrderDTO> fields_sorter) {
        this.fields_sorter = fields_sorter;
    }
    
    public QueryParamFilters toQueryParamFilters() {
        return new QueryParamFilters(
            this.is_paging, 
            this.paging != null ? this.paging.toPaging() : null, 
            this.fields_filter != null ?
                        this.fields_filter.stream()
                        .map(i -> i.toFilter())
                        .collect(Collectors.toList()) : null, 
            this.fields_sorter != null ?
                        this.fields_sorter.stream()
                        .map(i -> i.toSortOrder())
                        .collect(Collectors.toList()) : null
        );
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
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final QueryParamFiltersDTO other = (QueryParamFiltersDTO) obj;
        
        if (!(Objects.equals(this.is_paging, other.getIs_paging()))) {
            return false;
        }
        
        return Objects.equals(this.paging, other.getPaging());
    }

    @Override
    public String toString() {
        return "QueryParamFiltersDTO {"
                .concat("is_paging=")
                .concat(Boolean.toString(this.is_paging))
                .concat(", paging=")
                .concat(this.paging != null ? this.paging.toString():null)
                .concat(", fields_filter=")
                .concat(this.fields_filter != null ? this.fields_filter.toString():null)
                .concat(", fields_sorter=")
                .concat(this.fields_sorter != null ? this.fields_sorter.toString():null)
                .concat("}");
    }	
}
