package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
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
public class HakAksesRepositoryEJB implements Repository<HakAkses, QueryParamFilters, Filter> {
    
    @Inject
    private HakAksesRepositoryJPA hakAksesRepositoryJPA;

    @Override
    public HakAkses updateId(String idLama, HakAkses t) throws SQLException {
        return hakAksesRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public HakAkses save(HakAkses t) throws SQLException {
        return hakAksesRepositoryJPA.save(t);
    }

    @Override
    public HakAkses update(HakAkses t) throws SQLException {
        return hakAksesRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return hakAksesRepositoryJPA.delete(id);
    }

    @Override
    public List<HakAkses> getDaftarData(QueryParamFilters q) {
        return hakAksesRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return hakAksesRepositoryJPA.getJumlahData(f);
    }
    
}
