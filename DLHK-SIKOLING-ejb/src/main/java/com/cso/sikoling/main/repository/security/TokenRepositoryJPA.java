package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Token;
import com.cso.sikoling.abstraction.repository.RepositoryToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;


public class TokenRepositoryJPA implements RepositoryToken<Token, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public TokenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Token getToken(Credential c) throws SQLException {
        try {
            UserData userData = entityManager.createQuery("SELECT u FROM UserData u WHERE u.userName = :user AND u.password = :password", UserData.class)
                            .setParameter("user", c.getEmail())
                            .setParameter("password", c.getPassword())
                            .getSingleResult();
            
            AutorisasiData autorisasiData = entityManager.createNamedQuery("AutorisasiData.findByIdLama", AutorisasiData.class)
                                            .setParameter("idLama", userData.getId())
                                            .getSingleResult();
            
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.YEAR, 1); 
            Date nextYear = cal.getTime();
            SecretKey key = Jwts.SIG.HS256.key().build();
            
            
            String jwt = Jwts.builder()
                        .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(userData.getUserName()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasiData.getId())
                        .signWith(key)
                        .compact();
            Token token = new Token(jwt, jwt, 10000000L, autorisasiData.getId());
            return token;
        } catch (InvalidKeyException e) {
            return null;
        }   
        catch (Exception e) {
            return null;
        }
        
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
