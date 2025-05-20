package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class JwaTypeServiceBasic implements Service<JwaType> {
    
    private final Repository<JwaType, QueryParamFilters, Filter> repository;

    public JwaTypeServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public JwaType save(JwaType t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public JwaType update(JwaType t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public JwaType updateId(String idLama, JwaType t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<JwaType> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
