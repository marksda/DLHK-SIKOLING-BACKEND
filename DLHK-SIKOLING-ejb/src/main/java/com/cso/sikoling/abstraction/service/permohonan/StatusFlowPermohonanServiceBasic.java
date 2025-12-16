package com.cso.sikoling.abstraction.service.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.StatusFlowPermohonan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class StatusFlowPermohonanServiceBasic implements Service<StatusFlowPermohonan> {
    
    private final Repository<StatusFlowPermohonan, QueryParamFilters, Filter> repository;

    public StatusFlowPermohonanServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public StatusFlowPermohonan save(StatusFlowPermohonan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public StatusFlowPermohonan update(StatusFlowPermohonan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public StatusFlowPermohonan updateId(String idLama, StatusFlowPermohonan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<StatusFlowPermohonan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
