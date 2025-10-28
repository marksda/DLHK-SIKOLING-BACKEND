package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
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
public class OtorisasiRepositoryEJB implements Repository<Otorisasi, QueryParamFilters, Filter> {
    
    @Inject
    private OtorisasiRepositoryJPA autorisasiRepositoryJPA;

    @Override
    public Otorisasi updateId(String idLama, Otorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Otorisasi save(Otorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.save(t);
    }

    @Override
    public Otorisasi update(Otorisasi t) throws SQLException {
        return autorisasiRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return autorisasiRepositoryJPA.delete(id);
    }

    @Override
    public List<Otorisasi> getDaftarData(QueryParamFilters q) {
        return autorisasiRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return autorisasiRepositoryJPA.getJumlahData(f);
    }
    
}
