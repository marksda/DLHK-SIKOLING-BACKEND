package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class KategoriSkalaUsahaServiceBasic implements Service<KategoriSkalaUsaha> {
    
    private final Repository<KategoriSkalaUsaha, QueryParamFilters, Filter> repository;

    public KategoriSkalaUsahaServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public KategoriSkalaUsaha save(KategoriSkalaUsaha t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriSkalaUsaha update(KategoriSkalaUsaha t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriSkalaUsaha updateId(String idLama, KategoriSkalaUsaha t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriSkalaUsaha> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
