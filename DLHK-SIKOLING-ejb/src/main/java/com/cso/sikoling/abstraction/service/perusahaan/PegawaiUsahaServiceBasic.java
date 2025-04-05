package com.cso.sikoling.abstraction.service.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class PegawaiUsahaServiceBasic implements DAOService<Pegawai> {
    
    private final Repository<Pegawai, QueryParamFilters, Filter> repository;

    public PegawaiUsahaServiceBasic(Repository repository) {
        this.repository = repository;
    }
    

    @Override
    public Pegawai save(Pegawai t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Pegawai update(Pegawai t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Pegawai updateId(String idLama, Pegawai t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Pegawai> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
