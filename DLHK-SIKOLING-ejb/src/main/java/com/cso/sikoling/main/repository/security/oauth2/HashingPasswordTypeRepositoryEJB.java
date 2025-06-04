package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.HashingPasswordType;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class HashingPasswordTypeRepositoryEJB implements Repository<HashingPasswordType, QueryParamFilters, Filter> {
    
    @Inject
    private HashingPasswordTypeRepositoryJPA hashingPasswordTypeRepositoryJPA;

    @Override
    public HashingPasswordType updateId(String idLama, HashingPasswordType t) throws SQLException {
        return hashingPasswordTypeRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public HashingPasswordType save(HashingPasswordType t) throws SQLException {
        return hashingPasswordTypeRepositoryJPA.save(t);
    }

    @Override
    public HashingPasswordType update(HashingPasswordType t) throws SQLException {
        return hashingPasswordTypeRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return hashingPasswordTypeRepositoryJPA.delete(id);
    }

    @Override
    public List<HashingPasswordType> getDaftarData(QueryParamFilters q) {
        return hashingPasswordTypeRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return hashingPasswordTypeRepositoryJPA.getJumlahData(f);
    }
    
}
