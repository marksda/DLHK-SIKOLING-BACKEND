package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class PegawaiRepositoryEJB implements Repository<Pegawai, QueryParamFilters, Filter> {
    
    @Inject
    private PegawaiRepositoryJPA pegawaiRepositoryJPA;

    @Override
    public Pegawai updateId(String idLama, Pegawai t) throws SQLException {
        return pegawaiRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Pegawai save(Pegawai t) throws SQLException {
        return pegawaiRepositoryJPA.save(t);
    }

    @Override
    public Pegawai update(Pegawai t) throws SQLException {
        return pegawaiRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return pegawaiRepositoryJPA.delete(id);
    }

    @Override
    public List<Pegawai> getDaftarData(QueryParamFilters q) {
        return pegawaiRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return pegawaiRepositoryJPA.getJumlahData(f);
    }
    
}
