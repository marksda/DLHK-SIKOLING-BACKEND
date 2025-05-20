package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import java.sql.SQLException;
import java.util.List;

public interface Service<T> {
    T save(T t) throws SQLException;
    T update(T t) throws SQLException;
    T updateId(String idLama, T t) throws SQLException;
    boolean delete(String id) throws SQLException;
    List<T> getDaftarData(QueryParamFilters queryParamFilters);
    Long getJumlahData(List<Filter> queryParamFilters);
}