package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class PelakuUsahaServiceBasic implements DAOService<PelakuUsaha> {
    
    private final Repository<PelakuUsaha, QueryParamFilters, Filter> repository;

    public PelakuUsahaServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public PelakuUsaha save(PelakuUsaha t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public PelakuUsaha update(PelakuUsaha t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public PelakuUsaha updateId(String idLama, PelakuUsaha t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<PelakuUsaha> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
