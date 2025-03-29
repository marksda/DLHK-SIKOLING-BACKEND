package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
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
public class PelakuUsahaRepositoryEJB implements Repository<PelakuUsaha, QueryParamFilters, Filter> {
    
    @Inject
    private PelakuUsahaRepositoryJPA pelakuUsahaRepositoryJPA;

    @Override
    public PelakuUsaha updateId(String idLama, PelakuUsaha t) throws SQLException {
        return pelakuUsahaRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public PelakuUsaha save(PelakuUsaha t) throws SQLException {
        return pelakuUsahaRepositoryJPA.save(t);
    }

    @Override
    public PelakuUsaha update(PelakuUsaha t) throws SQLException {
        return pelakuUsahaRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return pelakuUsahaRepositoryJPA.delete(id);
    }

    @Override
    public List<PelakuUsaha> getDaftarData(QueryParamFilters q) {
        return pelakuUsahaRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return pelakuUsahaRepositoryJPA.getJumlahData(f);
    }
    
}
