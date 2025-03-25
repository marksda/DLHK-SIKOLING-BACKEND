package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Desa;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;

public class DesaServiceBasic implements AlamatService<Desa> {
    
    private final Repository<Desa, QueryParamFilters, Filter> repository;

    public DesaServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Desa save(Desa t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Desa update(Desa t) throws SQLException {
        return repository.update(t);
    }
    
    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Desa> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

    @Override
    public Desa updateId(String idLama, Desa t) throws SQLException {
        return repository.updateId(idLama, t);
    }

}
