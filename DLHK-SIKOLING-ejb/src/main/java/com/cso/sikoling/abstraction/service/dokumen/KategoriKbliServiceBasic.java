package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class KategoriKbliServiceBasic implements Service<KategoriKbli> {
    
    private final Repository<KategoriKbli, QueryParamFilters, Filter> repository;

    public KategoriKbliServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public KategoriKbli save(KategoriKbli t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public KategoriKbli update(KategoriKbli t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public KategoriKbli updateId(String idLama, KategoriKbli t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<KategoriKbli> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}