package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class JwaServiceBasic implements Service<Jwa> {
    
    private final Repository<Jwa, QueryParamFilters, Filter> repository;

    public JwaServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Jwa save(Jwa t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Jwa update(Jwa t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Jwa updateId(String idLama, Jwa t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Jwa> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
