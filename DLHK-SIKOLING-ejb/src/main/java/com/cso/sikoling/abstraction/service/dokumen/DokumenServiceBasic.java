package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class DokumenServiceBasic implements DAOService<Dokumen> {
    
    private final Repository<Dokumen, QueryParamFilters, Filter> repository;

    public DokumenServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public Dokumen save(Dokumen t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Dokumen update(Dokumen t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Dokumen updateId(String idLama, Dokumen t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Dokumen> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
