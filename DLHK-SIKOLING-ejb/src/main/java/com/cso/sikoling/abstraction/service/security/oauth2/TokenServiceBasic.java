package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.Repository;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.main.util.oauth2.KeyToolGenerator;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.crypto.SecretKey;


public class TokenServiceBasic implements TokenService<Token> {
    
    private final Repository<Token, QueryParamFilters, Filter> repositoryToken;  
    private final Repository<Key, QueryParamFilters, Filter> repositoryKey;

    public TokenServiceBasic(Repository repositoryToken, Repository repositoryKey) {
        this.repositoryToken = repositoryToken;
        this.repositoryKey = repositoryKey;
    }
    
    @Override
    public Token generateToken(Key key, Map<String, Object> header, Map<String, Object> payload) {
        Token token;        
        String idToken = generateIdToken();
        
        switch (key.getId_jwa()) {
            case "01" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(secretKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "02" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(secretKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "03" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                        key.getSecred_key(), key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(secretKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "04" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "05" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "06" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "07" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "08" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "09" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "10" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "11" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "12" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "36" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                String jwt = Jwts.builder()                        
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .signWith(privateKey)
                        .compact();
                token = new Token(idToken, jwt, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat")); 
            }
            case "13" -> {   
                AeadAlgorithm enc = Jwts.ENC.A128CBC_HS256;
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToAESSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
                String message = (String) payload.get("content");
                byte[] content = message.getBytes(StandardCharsets.UTF_8);
               
                String jwe = Jwts.builder()
                        .header().add("typ", "JOSE").and()
                        .content(content, "text/plain")
                        .encryptWith(secretKey, enc)
                        .compact();
                
                token = new Token(idToken, jwe, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            case "19" -> {  
                PublicKey publicKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                        key.getPublic_key(), 
                                        key.getId_jwa(), 
                                        key.getId_encoding_scheme()
                                    );
                
                KeyAlgorithm<PublicKey, PrivateKey> alg = Jwts.KEY.RSA_OAEP_256; //or RSA_OAEP or RSA_OAEP_256      
                
                AeadAlgorithm enc = Jwts.ENC.A256GCM;
                               
                String jwe = Jwts.builder()
                        .header().add(header).and()
                        .id(idToken)
                        .claims(payload)
                        .encryptWith(publicKey, alg, enc)
                        .compact();
                
                token = new Token(idToken, jwe, null, ((Date) payload.get("exp")).getTime(), (Date) payload.get("iat"));
            }
            default -> throw new AssertionError();
        }   

        
        return token;
    }

    @Override
    public Token save(Token t) throws SQLException {
        return this.repositoryToken.save(t);
    }

    @Override
    public Token update(Token t) throws SQLException {
        return this.repositoryToken.update(t);
    }

    @Override
    public Token updateId(String idLama, Token t) throws SQLException {
        return this.repositoryToken.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return this.repositoryToken.delete(id);
    }

    @Override
    public List getDaftarData(QueryParamFilters queryParamFilters) {
        return this.repositoryToken.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List queryParamFilters) {
        return this.repositoryToken.getJumlahData(queryParamFilters);
    }

    @Override
    public Claims validateAccessToken(String accessToken) {
        Locator<java.security.Key> keyLocator = new KeyLocator();
        
        Jws<Claims> jws;
        
        try {
            jws = (Jws<Claims>) Jwts.parser()
                    .keyLocator(keyLocator)
                    .build()
                    .parseSignedClaims(accessToken);
            return jws.getPayload();
        } catch (JwtException e) {
            return null;
        }
        
    }
    
    private String generateIdToken() {
        UUID uuid = UuidCreator.getTimeOrderedEpoch();
        return uuid.toString();
    }
    
    private class KeyLocator extends LocatorAdapter<java.security.Key> {

        @Override
        protected java.security.Key locate(JwsHeader header) {
            List<Filter> fields_filter = new ArrayList<>();
            Filter filter = new Filter("id", header.getKeyId());
            fields_filter.add(filter);
            QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
            java.security.Key decodeKey;
            
            try {
                Key key = repositoryKey.getDaftarData(qFilter).getFirst();
                
                switch (key.getId_jwa()) {
                    case "01" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                                key.getSecred_key(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "02" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                                key.getSecred_key(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "03" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToHmacSecretKey(
                                key.getSecred_key(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "04" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "05" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "06" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "07" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "08" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "09" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "10" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "11" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "12" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    case "36" -> {
                        decodeKey = KeyToolGenerator.convertStringKeyToPublicKey(
                                key.getPublic_key(), 
                                key.getId_jwa(), 
                                key.getId_encoding_scheme()
                            );
                    }
                    default -> {
                        decodeKey = null;
                    }
                }
                
                return decodeKey;
            } catch (NoSuchElementException e) {
                return null;
            }
            
        }
        
    }
}
