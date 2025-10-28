package com.cso.sikoling.abstraction.service.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class OtorisasiServiceBasic implements Service<Otorisasi> {
    
    private final Repository<Otorisasi, QueryParamFilters, Filter> repository;

    public OtorisasiServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Otorisasi save(Otorisasi t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Otorisasi update(Otorisasi t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Otorisasi updateId(String idLama, Otorisasi t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Otorisasi> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
