package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.repository.TokenRepository;
import com.cso.sikoling.abstraction.service.TokenService;
import io.jsonwebtoken.Jwts;
import java.security.PrivateKey;
import javax.crypto.SecretKey;


public class TokenServiceBasic implements TokenService<Token> {
    
    private final TokenRepository<Token, QueryParamFilters, Filter> repository;  

    public TokenServiceBasic(TokenRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Token generateToken(Key key) {
        Token token = null;
/*
        switch (key.getId_jwa()) {
            case "01" -> {   
                SecretKey secretKey = convertStringToSecretKey(keyData.getSecretKey(), encodingScheme);
                jwt = Jwts.builder()
                        .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasiData.getHakAkses().getId()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasiData.getId())
                        .signWith(secretKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "02" -> {   
                SecretKey secretKey = convertStringToSecretKey(keyData.getSecretKey(), encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(secretKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "03" -> {   
                SecretKey secretKey = convertStringToSecretKey(keyData.getSecretKey(), encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(secretKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "04" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "04",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "05" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "05",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "06" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "06",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "07" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "07",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "08" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "08",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "09" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "09",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "10" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "10",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "11" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "11",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "12" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "12",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            case "36" -> {   
                PrivateKey privateKey = convertStringToPrivateKey(keyData.getPrivateKey(), "36",  encodingScheme);
                jwt = Jwts.builder()
                                .header().keyId(autorisasiData.getId()).add("typ", "JWT").and()
                                .issuer("DLHK Sidoarjo")
                                .subject("sikoling")
                                .audience().add(autorisasiData.getHakAkses().getId()).and()
                                .expiration(nextYear)
                                .issuedAt(today)
                                .id(autorisasiData.getId())
                                .signWith(privateKey)
                                .compact();
                token = new Token(jwt, null, 10000000L, autorisasiData.getId());
            }
            default -> throw new AssertionError();
        }   
*/
        
        return token;
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
