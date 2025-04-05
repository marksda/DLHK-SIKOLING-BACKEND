package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class JabatanServiceBasic implements DAOService<Jabatan> {
    
    private final Repository<Jabatan, QueryParamFilters, Filter> repository;

    public JabatanServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public Jabatan save(Jabatan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Jabatan update(Jabatan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Jabatan updateId(String idLama, Jabatan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Jabatan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
