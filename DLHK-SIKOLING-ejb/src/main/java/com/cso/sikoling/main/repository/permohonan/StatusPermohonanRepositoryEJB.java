package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.StatusPermohonan;
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
public class StatusPermohonanRepositoryEJB implements Repository<StatusPermohonan, QueryParamFilters, Filter> {
    
    @Inject
    private StatusPermohonanRepositoryJPA statusPermohonanRepositoryJPA;

    @Override
    public StatusPermohonan updateId(String idLama, StatusPermohonan t) throws SQLException {
        return statusPermohonanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public StatusPermohonan save(StatusPermohonan t) throws SQLException {
        return statusPermohonanRepositoryJPA.save(t);
    }

    @Override
    public StatusPermohonan update(StatusPermohonan t) throws SQLException {
        return statusPermohonanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return statusPermohonanRepositoryJPA.delete(id);
    }

    @Override
    public List<StatusPermohonan> getDaftarData(QueryParamFilters q) {
        return statusPermohonanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return statusPermohonanRepositoryJPA.getJumlahData(f);
    }
    
}
