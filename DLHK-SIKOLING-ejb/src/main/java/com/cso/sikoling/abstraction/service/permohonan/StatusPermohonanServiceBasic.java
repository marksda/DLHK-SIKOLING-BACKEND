package com.cso.sikoling.abstraction.service.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.StatusPermohonan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class StatusPermohonanServiceBasic implements Service<StatusPermohonan> {
    
    private final Repository<StatusPermohonan, QueryParamFilters, Filter> repository;

    public StatusPermohonanServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public StatusPermohonan save(StatusPermohonan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public StatusPermohonan update(StatusPermohonan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public StatusPermohonan updateId(String idLama, StatusPermohonan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<StatusPermohonan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
