package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
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
public class KeyRepositoryEJB implements Repository<Key, QueryParamFilters, Filter> {
    
    @Inject
    private KeyRepositoryJPA keyRepository;

    @Override
    public Key updateId(String idLama, Key t) throws SQLException {
        return keyRepository.updateId(idLama, t);
    }

    @Override
    public Key save(Key t) throws SQLException {
        return keyRepository.save(t);
    }

    @Override
    public Key update(Key t) throws SQLException {
        return keyRepository.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return keyRepository.delete(id);
    }

    @Override
    public List<Key> getDaftarData(QueryParamFilters q) {
        return keyRepository.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return keyRepository.getJumlahData(f);
    }
    
}
