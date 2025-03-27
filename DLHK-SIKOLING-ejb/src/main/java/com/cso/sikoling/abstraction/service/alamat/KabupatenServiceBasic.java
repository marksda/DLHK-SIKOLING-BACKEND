package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;

public class KabupatenServiceBasic implements DAOService<Kabupaten> {
    
    private final Repository<Kabupaten, QueryParamFilters, Filter> repository;

    public KabupatenServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Kabupaten save(Kabupaten t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Kabupaten update(Kabupaten t) throws SQLException {
        return repository.update(t);
    }
    

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Kabupaten> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

    @Override
    public Kabupaten updateId(String idLama, Kabupaten t) throws SQLException {
        return repository.updateId(idLama, t);
    }

}
