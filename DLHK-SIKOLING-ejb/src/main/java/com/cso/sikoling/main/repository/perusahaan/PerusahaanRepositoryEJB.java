package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
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
public class PerusahaanRepositoryEJB implements Repository<Perusahaan, QueryParamFilters, Filter> {
    
    @Inject
    private PerusahaanRepositoryJPA perusahaanRepositoryJPA;

    @Override
    public Perusahaan updateId(String idLama, Perusahaan t) throws SQLException {
        return perusahaanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Perusahaan save(Perusahaan t) throws SQLException {
        return perusahaanRepositoryJPA.save(t);
    }

    @Override
    public Perusahaan update(Perusahaan t) throws SQLException {
        return perusahaanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return perusahaanRepositoryJPA.delete(id);
    }

    @Override
    public List<Perusahaan> getDaftarData(QueryParamFilters q) {
        return perusahaanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return perusahaanRepositoryJPA.getJumlahData(f);
    }
    
}
