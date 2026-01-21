package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.dokumen.MasterDokumen;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class MasterDokumenServiceBasic implements Service<MasterDokumen> {
    
    private final Repository<MasterDokumen, QueryParamFilters, Filter> repository;

    public MasterDokumenServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public MasterDokumen save(MasterDokumen t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public MasterDokumen update(MasterDokumen t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public MasterDokumen updateId(String idLama, MasterDokumen t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<MasterDokumen> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
