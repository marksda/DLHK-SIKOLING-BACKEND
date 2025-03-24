package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Propinsi;
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
public class PropinsiRepositoryEJB implements Repository<Propinsi, QueryParamFilters, Filter> {
    
    @Inject
    private PropinsiRepositoryJPA propinsiRepositoryJPA;

    @Override
    public Propinsi updateId(String idLama, Propinsi t) throws SQLException {
        return propinsiRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Propinsi save(Propinsi t) throws SQLException {
        return propinsiRepositoryJPA.save(t);
    }

    @Override
    public Propinsi update(Propinsi t) throws SQLException {
        return propinsiRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return propinsiRepositoryJPA.delete(id);
    }

    @Override
    public List<Propinsi> getDaftarData(QueryParamFilters q) {
        return propinsiRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return propinsiRepositoryJPA.getJumlahData(f);
    }
    
}
