package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.repository.RepositoryToken;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.main.Infrastructure;
import io.jsonwebtoken.Claims;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class TokenRepositoryEJB implements RepositoryToken<Token, QueryParamFilters, Filter> {
    
    @Inject
    private TokenRepositoryJPA tokenRepositoriJPA;

    @Override
    public Token getToken(Credential c) throws SQLException {
        return tokenRepositoriJPA.getToken(c);
    }

    @Override
    public Token save(Token t) throws SQLException {
        return tokenRepositoriJPA.save(t);
    }

    @Override
    public Token update(Token t) throws SQLException {
        return tokenRepositoriJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return tokenRepositoriJPA.delete(id);
    }

    @Override
    public Token updateId(String idLama, Token t) throws SQLException {
        return tokenRepositoriJPA.updateId(idLama, t);
    }

    @Override
    public List<Token> getDaftarData(QueryParamFilters q) {
        return tokenRepositoriJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return tokenRepositoriJPA.getJumlahData(f);
    }

    @Override
    public Claims validateAccessToken(String accessToken) {
        return tokenRepositoriJPA.validateAccessToken(accessToken);
    }

}
