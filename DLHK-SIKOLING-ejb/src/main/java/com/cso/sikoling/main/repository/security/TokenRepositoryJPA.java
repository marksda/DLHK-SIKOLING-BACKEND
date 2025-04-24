package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Token;
import com.cso.sikoling.abstraction.repository.RepositoryToken;
import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;


public class TokenRepositoryJPA implements RepositoryToken<Token, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public TokenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Token getToken(Credential c) throws SQLException {
        return null;
    }

    @Override
    public Token save(Token t) throws SQLException {
        return null;
    }

    @Override
    public Token update(Token t) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return true;
    }

    @Override
    public Token updateId(String idLama, Token t) throws SQLException {
        return null;
    }

    @Override
    public List<Token> getDaftarData(QueryParamFilters q) {
        return null;
    }

    @Override
    public Long getJumlahData(List f) {
        return null;
    }

}
