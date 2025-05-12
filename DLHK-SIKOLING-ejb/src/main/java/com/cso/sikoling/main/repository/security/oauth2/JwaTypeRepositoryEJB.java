package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
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
public class JwaTypeRepositoryEJB implements Repository<JwaType, QueryParamFilters, Filter> {
    
    @Inject
    private JwaTypeRepositoryJPA jwaTypeRepositoryJPA;

    @Override
    public JwaType updateId(String idLama, JwaType t) throws SQLException {
        return jwaTypeRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public JwaType save(JwaType t) throws SQLException {
        return jwaTypeRepositoryJPA.save(t);
    }

    @Override
    public JwaType update(JwaType t) throws SQLException {
        return jwaTypeRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return jwaTypeRepositoryJPA.delete(id);
    }

    @Override
    public List<JwaType> getDaftarData(QueryParamFilters q) {
        return jwaTypeRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return jwaTypeRepositoryJPA.getJumlahData(f);
    }
    
}
