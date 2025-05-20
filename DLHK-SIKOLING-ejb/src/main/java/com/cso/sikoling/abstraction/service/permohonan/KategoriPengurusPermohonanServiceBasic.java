package com.cso.sikoling.abstraction.service.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class KategoriPengurusPermohonanServiceBasic implements Service<KategoriPengurusPermohonan> {
    
    private final Repository<KategoriPengurusPermohonan, QueryParamFilters, Filter> repository;

    public KategoriPengurusPermohonanServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public KategoriPengurusPermohonan save(KategoriPengurusPermohonan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriPengurusPermohonan update(KategoriPengurusPermohonan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriPengurusPermohonan updateId(String idLama, KategoriPengurusPermohonan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriPengurusPermohonan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
