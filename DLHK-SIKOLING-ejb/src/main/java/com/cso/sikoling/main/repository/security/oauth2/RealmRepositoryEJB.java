package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
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
public class RealmRepositoryEJB implements Repository<Realm, QueryParamFilters, Filter> {
    
    @Inject
    private RealmRepositoryJPA realmRepositoryJPA;

    @Override
    public Realm updateId(String idLama, Realm t) throws SQLException {
        return realmRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Realm save(Realm t) throws SQLException {
        return realmRepositoryJPA.save(t);
    }

    @Override
    public Realm update(Realm t) throws SQLException {
        return realmRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return realmRepositoryJPA.delete(id);
    }

    @Override
    public List<Realm> getDaftarData(QueryParamFilters q) {
        return realmRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return realmRepositoryJPA.getJumlahData(f);
    }
    
}
