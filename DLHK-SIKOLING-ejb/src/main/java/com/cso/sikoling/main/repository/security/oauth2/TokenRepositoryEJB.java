package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.repository.RepositoryToken;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
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
    public Token getToken(Credential c, String idRealm, String idKey, String encodingScheme) {
        return tokenRepositoriJPA.getToken(c, idRealm, idKey, encodingScheme);
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

    @Override
    public Key generateKey(String idRealm, String idJwa, String encodingScheme) {
        return tokenRepositoriJPA.generateKey(idRealm, idJwa, encodingScheme);
    }

}
