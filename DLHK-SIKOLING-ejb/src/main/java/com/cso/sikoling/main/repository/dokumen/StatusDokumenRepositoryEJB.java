package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.StatusDokumen;
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
public class StatusDokumenRepositoryEJB implements Repository<StatusDokumen, QueryParamFilters, Filter> {
    
    @Inject
    private StatusDokumenRepositoryJPA repositoryJPA;

    @Override
    public StatusDokumen updateId(String idLama, StatusDokumen t) throws SQLException {
        return repositoryJPA.updateId(idLama, t);
    }

    @Override
    public StatusDokumen save(StatusDokumen t) throws SQLException {
        return repositoryJPA.save(t);
    }

    @Override
    public StatusDokumen update(StatusDokumen t) throws SQLException {
        return repositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repositoryJPA.delete(id);
    }

    @Override
    public List<StatusDokumen> getDaftarData(QueryParamFilters q) {
        return repositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return repositoryJPA.getJumlahData(f);
    }
    
}
