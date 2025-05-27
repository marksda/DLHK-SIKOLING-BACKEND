package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class UserServiceBasic implements Service<User> {
    
    private final Repository<User, QueryParamFilters, Filter> repository;

    public UserServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public User save(User t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public User update(User t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public User updateId(String idLama, User t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<User> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
