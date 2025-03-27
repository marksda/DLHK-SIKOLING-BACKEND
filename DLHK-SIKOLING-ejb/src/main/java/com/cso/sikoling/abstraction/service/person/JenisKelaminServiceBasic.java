package com.cso.sikoling.abstraction.service.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class JenisKelaminServiceBasic implements DAOService<JenisKelamin> {
    
    private final Repository<JenisKelamin, QueryParamFilters, Filter> repository;

    public JenisKelaminServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public JenisKelamin save(JenisKelamin t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public JenisKelamin update(JenisKelamin t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public JenisKelamin updateId(String idLama, JenisKelamin t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<JenisKelamin> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
