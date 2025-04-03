package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class PerusahaanServiceBasic implements DAOService<Perusahaan> {
    
    private final Repository<Perusahaan, QueryParamFilters, Filter> repository;

    public PerusahaanServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public Perusahaan save(Perusahaan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Perusahaan update(Perusahaan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Perusahaan updateId(String idLama, Perusahaan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Perusahaan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
