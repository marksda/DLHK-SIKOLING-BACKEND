package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Kabupaten;
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
public class KabupatenRepositoryEJB implements Repository<Kabupaten, QueryParamFilters, Filter> {
    
    @Inject
    private KabupatenRepositoryJPA kabupatenRepositoryJPA;

    @Override
    public Kabupaten save(Kabupaten t) throws SQLException {
        return kabupatenRepositoryJPA.save(t);
    }

    @Override
    public Kabupaten update(Kabupaten t) throws SQLException {
        return kabupatenRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kabupatenRepositoryJPA.delete(id);
    }

    @Override
    public Kabupaten updateId(String idLama, Kabupaten t) throws SQLException {
        return kabupatenRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public List<Kabupaten> getDaftarData(QueryParamFilters q) {
        return kabupatenRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kabupatenRepositoryJPA.getJumlahData(f);
    }

}
