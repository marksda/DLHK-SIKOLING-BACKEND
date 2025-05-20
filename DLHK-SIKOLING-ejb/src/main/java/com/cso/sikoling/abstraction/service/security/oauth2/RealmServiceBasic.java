package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class RealmServiceBasic implements Service<Realm> {
    
    private final Repository<Realm, QueryParamFilters, Filter> repository;

    public RealmServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Realm save(Realm t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Realm update(Realm t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Realm updateId(String idLama, Realm t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Realm> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
