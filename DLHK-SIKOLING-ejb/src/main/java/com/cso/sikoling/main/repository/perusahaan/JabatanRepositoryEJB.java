package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
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
public class JabatanRepositoryEJB implements Repository<Jabatan, QueryParamFilters, Filter> {
    
    @Inject
    private JabatanRepositoryJPA jabatanRepositoryJPA;

    @Override
    public Jabatan updateId(String idLama, Jabatan t) throws SQLException {
        return jabatanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Jabatan save(Jabatan t) throws SQLException {
        return jabatanRepositoryJPA.save(t);
    }

    @Override
    public Jabatan update(Jabatan t) throws SQLException {
        return jabatanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return jabatanRepositoryJPA.delete(id);
    }

    @Override
    public List<Jabatan> getDaftarData(QueryParamFilters q) {
        return jabatanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return jabatanRepositoryJPA.getJumlahData(f);
    }
    
}
