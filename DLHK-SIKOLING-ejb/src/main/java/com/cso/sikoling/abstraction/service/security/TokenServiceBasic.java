package com.cso.sikoling.abstraction.service.security;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.RepositoryToken;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;
import java.util.List;


public class TokenServiceBasic implements DAOTokenService<Token> {
    
    private final RepositoryToken<Token, QueryParamFilters, Filter> repository;  

    public TokenServiceBasic(RepositoryToken repository) {
        this.repository = repository;
    }
    
    @Override
    public Token getToken(Credential c) throws SQLException {
        return this.repository.getToken(c);
    }

    @Override
    public Token save(Token t) throws SQLException {
        return this.repository.save(t);
    }

    @Override
    public Token update(Token t) throws SQLException {
        return this.repository.update(t);
    }

    @Override
    public Token updateId(String idLama, Token t) throws SQLException {
        return this.repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return this.repository.delete(id);
    }

    @Override
    public List getDaftarData(QueryParamFilters queryParamFilters) {
        return this.repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List queryParamFilters) {
        return this.repository.getJumlahData(queryParamFilters);
    }

    @Override
    public Claims validateAccessToken(String accessToken) {
        return this.repository.validateAccessToken(accessToken);
    }

}
