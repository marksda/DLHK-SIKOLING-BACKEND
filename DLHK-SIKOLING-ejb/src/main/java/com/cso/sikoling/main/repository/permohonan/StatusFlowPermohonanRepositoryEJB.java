package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.StatusFlowPermohonan;
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
public class StatusFlowPermohonanRepositoryEJB implements Repository<StatusFlowPermohonan, QueryParamFilters, Filter> {
    
    @Inject
    private StatusFlowPermohonanRepositoryJPA statusFlowPermohonanRepositoryJPA;

    @Override
    public StatusFlowPermohonan updateId(String idLama, StatusFlowPermohonan t) throws SQLException {
        return statusFlowPermohonanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public StatusFlowPermohonan save(StatusFlowPermohonan t) throws SQLException {
        return statusFlowPermohonanRepositoryJPA.save(t);
    }

    @Override
    public StatusFlowPermohonan update(StatusFlowPermohonan t) throws SQLException {
        return statusFlowPermohonanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return statusFlowPermohonanRepositoryJPA.delete(id);
    }

    @Override
    public List<StatusFlowPermohonan> getDaftarData(QueryParamFilters q) {
        return statusFlowPermohonanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return statusFlowPermohonanRepositoryJPA.getJumlahData(f);
    }
    
}
