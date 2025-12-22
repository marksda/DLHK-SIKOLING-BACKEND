package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.StatusDokumen;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class StatusDokumenServiceBasic implements Service<StatusDokumen> {
    
    private final Repository<StatusDokumen, QueryParamFilters, Filter> repository;

    public StatusDokumenServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public StatusDokumen save(StatusDokumen t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public StatusDokumen update(StatusDokumen t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public StatusDokumen updateId(String idLama, StatusDokumen t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<StatusDokumen> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
