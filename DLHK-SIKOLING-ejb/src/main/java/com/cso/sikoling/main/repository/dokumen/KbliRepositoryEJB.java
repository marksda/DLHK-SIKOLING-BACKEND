package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.Kbli;
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
public class KbliRepositoryEJB implements Repository<Kbli, QueryParamFilters, Filter> {
    
    @Inject
    private KbliRepositoryJPA repositoryJPA;

    @Override
    public Kbli updateId(String idLama, Kbli t) throws SQLException {
        return repositoryJPA.updateId(idLama, t);
    }

    @Override
    public Kbli save(Kbli t) throws SQLException {
        return repositoryJPA.save(t);
    }

    @Override
    public Kbli update(Kbli t) throws SQLException {
        return repositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repositoryJPA.delete(id);
    }

    @Override
    public List<Kbli> getDaftarData(QueryParamFilters q) {
        return repositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return repositoryJPA.getJumlahData(f);
    }
    
}
