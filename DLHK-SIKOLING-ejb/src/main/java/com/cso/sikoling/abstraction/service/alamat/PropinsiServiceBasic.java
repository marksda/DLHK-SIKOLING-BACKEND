package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;

public class PropinsiServiceBasic implements Service<Propinsi> {
    
    private final Repository<Propinsi, QueryParamFilters, Filter> repository;

    public PropinsiServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Propinsi save(Propinsi t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Propinsi update(Propinsi t) throws SQLException {
        return repository.update(t);
    }
    

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Propinsi> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

    @Override
    public Propinsi updateId(String idLama, Propinsi t) throws SQLException {
        return repository.updateId(idLama, t);
    }

}
