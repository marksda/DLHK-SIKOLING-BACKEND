package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.HashingPasswordType;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class HashingPasswordTypeServiceBasic implements Service<HashingPasswordType> {
    
    private final Repository<HashingPasswordType, QueryParamFilters, Filter> repository;

    public HashingPasswordTypeServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public HashingPasswordType save(HashingPasswordType t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public HashingPasswordType update(HashingPasswordType t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public HashingPasswordType updateId(String idLama, HashingPasswordType t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<HashingPasswordType> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
