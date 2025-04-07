package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
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
public class DokumenRepositoryEJB implements Repository<Dokumen, QueryParamFilters, Filter> {
    
    @Inject
    private DokumenRepositoryJPA dokumenRepositoryJPA;

    @Override
    public Dokumen updateId(String idLama, Dokumen t) throws SQLException {
        return dokumenRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Dokumen save(Dokumen t) throws SQLException {
        return dokumenRepositoryJPA.save(t);
    }

    @Override
    public Dokumen update(Dokumen t) throws SQLException {
        return dokumenRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return dokumenRepositoryJPA.delete(id);
    }

    @Override
    public List<Dokumen> getDaftarData(QueryParamFilters q) {
        return dokumenRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return dokumenRepositoryJPA.getJumlahData(f);
    }
    
}
