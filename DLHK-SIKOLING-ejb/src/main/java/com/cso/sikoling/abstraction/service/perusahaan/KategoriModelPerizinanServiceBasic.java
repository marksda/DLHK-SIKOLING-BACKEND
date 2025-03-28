package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class KategoriModelPerizinanServiceBasic implements DAOService<KategoriModelPerizinan> {
    
    private final Repository<KategoriModelPerizinan, QueryParamFilters, Filter> repository;

    public KategoriModelPerizinanServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public KategoriModelPerizinan save(KategoriModelPerizinan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriModelPerizinan update(KategoriModelPerizinan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriModelPerizinan updateId(String idLama, KategoriModelPerizinan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriModelPerizinan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
