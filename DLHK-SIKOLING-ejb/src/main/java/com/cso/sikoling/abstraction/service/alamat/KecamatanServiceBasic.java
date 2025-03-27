package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;

public class KecamatanServiceBasic implements DAOService<Kecamatan> {
    
    private final Repository<Kecamatan, QueryParamFilters, Filter> repository;

    public KecamatanServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Kecamatan save(Kecamatan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Kecamatan update(Kecamatan t) throws SQLException {
        return repository.update(t);
    }
    
    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Kecamatan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

    @Override
    public Kecamatan updateId(String idLama, Kecamatan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

}
