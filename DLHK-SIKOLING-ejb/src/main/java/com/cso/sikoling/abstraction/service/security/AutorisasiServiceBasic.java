package com.cso.sikoling.abstraction.service.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class AutorisasiServiceBasic implements Service<Autorisasi> {
    
    private final Repository<Autorisasi, QueryParamFilters, Filter> repository;

    public AutorisasiServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Autorisasi save(Autorisasi t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Autorisasi update(Autorisasi t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Autorisasi updateId(String idLama, Autorisasi t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Autorisasi> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
