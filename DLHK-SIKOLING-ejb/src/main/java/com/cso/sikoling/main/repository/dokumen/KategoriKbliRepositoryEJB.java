package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
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
public class KategoriKbliRepositoryEJB implements Repository<KategoriKbli, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriKbliRepositoryJPA repositoryJPA;

    @Override
    public KategoriKbli updateId(String idLama, KategoriKbli t) throws SQLException {
        return repositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriKbli save(KategoriKbli t) throws SQLException {
        return repositoryJPA.save(t);
    }

    @Override
    public KategoriKbli update(KategoriKbli t) throws SQLException {
        return repositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repositoryJPA.delete(id);
    }

    @Override
    public List<KategoriKbli> getDaftarData(QueryParamFilters q) {
        return repositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return repositoryJPA.getJumlahData(f);
    }
    
}
