package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class VersiKbliServiceBasic implements Service<VersiKbli> {
    
    private final Repository<VersiKbli, QueryParamFilters, Filter> repository;

    public VersiKbliServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public VersiKbli save(VersiKbli t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public VersiKbli update(VersiKbli t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public VersiKbli updateId(String idLama, VersiKbli t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<VersiKbli> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
