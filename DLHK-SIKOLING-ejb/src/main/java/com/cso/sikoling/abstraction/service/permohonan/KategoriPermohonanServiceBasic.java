package com.cso.sikoling.abstraction.service.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class KategoriPermohonanServiceBasic implements DAOService<KategoriPermohonan> {
    
    private final Repository<KategoriPermohonan, QueryParamFilters, Filter> repository;

    public KategoriPermohonanServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public KategoriPermohonan save(KategoriPermohonan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriPermohonan update(KategoriPermohonan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriPermohonan updateId(String idLama, KategoriPermohonan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriPermohonan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
