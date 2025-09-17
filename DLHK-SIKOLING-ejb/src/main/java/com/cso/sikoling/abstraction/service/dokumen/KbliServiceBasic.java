package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.Kbli;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class KbliServiceBasic implements Service<Kbli> {
    
    private final Repository<Kbli, QueryParamFilters, Filter> repository;

    public KbliServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Kbli save(Kbli t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Kbli update(Kbli t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Kbli updateId(String idLama, Kbli t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Kbli> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
