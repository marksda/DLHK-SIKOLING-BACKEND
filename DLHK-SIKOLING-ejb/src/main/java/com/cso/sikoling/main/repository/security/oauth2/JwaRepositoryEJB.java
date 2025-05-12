package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
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
public class JwaRepositoryEJB implements Repository<Jwa, QueryParamFilters, Filter> {
    
    @Inject
    private JwaRepositoryJPA jwaRepositoryJPA;

    @Override
    public Jwa updateId(String idLama, Jwa t) throws SQLException {
        return jwaRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public Jwa save(Jwa t) throws SQLException {
        return jwaRepositoryJPA.save(t);
    }

    @Override
    public Jwa update(Jwa t) throws SQLException {
        return jwaRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return jwaRepositoryJPA.delete(id);
    }

    @Override
    public List<Jwa> getDaftarData(QueryParamFilters q) {
        return jwaRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return jwaRepositoryJPA.getJumlahData(f);
    }
    
}
