package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
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
public class AutorisasiRepositoryEJB implements Repository<Autorisasi, QueryParamFilters, Filter> {
    
    @Inject
    private AutorisasiRepositoryJPA autorisasiRepositoryJPA;

    @Override
    public Autorisasi updateId(String idLama, Autorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Autorisasi save(Autorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.save(t);
    }

    @Override
    public Autorisasi update(Autorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return autorisasiRepositoryJPA.delete(id);
    }

    @Override
    public List<Autorisasi> getDaftarData(QueryParamFilters q) {
        return autorisasiRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return autorisasiRepositoryJPA.getJumlahData(f);
    }
    
}
