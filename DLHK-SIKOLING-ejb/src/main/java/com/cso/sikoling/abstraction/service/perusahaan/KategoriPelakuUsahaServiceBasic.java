package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class KategoriPelakuUsahaServiceBasic implements DAOService<KategoriPelakuUsaha> {
    
    private final Repository<KategoriPelakuUsaha, QueryParamFilters, Filter> repository;

    public KategoriPelakuUsahaServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public KategoriPelakuUsaha save(KategoriPelakuUsaha t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriPelakuUsaha update(KategoriPelakuUsaha t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriPelakuUsaha updateId(String idLama, KategoriPelakuUsaha t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriPelakuUsaha> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
