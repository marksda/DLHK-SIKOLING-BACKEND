package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
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
public class UserRepositoryEJB implements Repository<User, QueryParamFilters, Filter> {
    
    @Inject
    private UserRepositoryJPA userRepositoryJPA;

    @Override
    public User updateId(String idLama, User t) throws SQLException {
        return userRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public User save(User t) throws SQLException {
        return userRepositoryJPA.save(t);
    }

    @Override
    public User update(User t) throws SQLException {
        return userRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return userRepositoryJPA.delete(id);
    }

    @Override
    public List<User> getDaftarData(QueryParamFilters q) {
        return userRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return userRepositoryJPA.getJumlahData(f);
    }
    
}
