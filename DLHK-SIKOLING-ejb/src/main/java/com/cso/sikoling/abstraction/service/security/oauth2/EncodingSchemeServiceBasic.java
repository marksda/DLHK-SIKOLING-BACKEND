package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.EncodingScheme;
import com.cso.sikoling.abstraction.repository.Repository;

import com.cso.sikoling.abstraction.service.DAOService;
import java.sql.SQLException;
import java.util.List;


public class EncodingSchemeServiceBasic implements DAOService<EncodingScheme> {
    
    private final Repository<EncodingScheme, QueryParamFilters, Filter> repository;

    public EncodingSchemeServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public EncodingScheme save(EncodingScheme t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public EncodingScheme update(EncodingScheme t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public EncodingScheme updateId(String idLama, EncodingScheme t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<EncodingScheme> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
