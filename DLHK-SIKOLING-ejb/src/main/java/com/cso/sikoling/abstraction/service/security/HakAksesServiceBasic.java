package com.cso.sikoling.abstraction.service.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class HakAksesServiceBasic implements Service<HakAkses> {
    
    private final Repository<HakAkses, QueryParamFilters, Filter> repository;

    public HakAksesServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public HakAkses save(HakAkses t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public HakAkses update(HakAkses t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public HakAkses updateId(String idLama, HakAkses t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<HakAkses> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
