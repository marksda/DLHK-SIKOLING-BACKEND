package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.Repository;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.main.util.oauth2.KeyToolGenerator;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.SecretKey;


public class TokenServiceBasic implements TokenService<Token> {
    
    private final Repository<Token, QueryParamFilters, Filter> repository;  

    public TokenServiceBasic(Repository repository) {
        this.repository = repository;
    }
    
    @Override
    public Token generateToken(Key key, Autorisasi autorisasi) {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 1); 
        Date nextYear = cal.getTime();
        String jwt;
        Token token;

        switch (key.getId_jwa()) {
            case "01" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(secretKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "02" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(secretKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "03" -> {   
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToSecretKey(
                        key.getSecred_key(), key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(secretKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "04" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "05" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "06" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "07" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "08" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "09" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "10" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "11" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "12" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "36" -> {   
                PrivateKey privateKey = KeyToolGenerator.convertStringKeyToPrivateKey(
                        key.getPrivate_key(), key.getId_jwa(),  key.getId_encoding_scheme()
                    );
                jwt = Jwts.builder()
                        .header().keyId(key.getId()).add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasi.getId())
                        .signWith(privateKey)
                        .compact();
                token = new Token(jwt, null, 10000000L, autorisasi.getId());
            }
            case "13" -> {   
                AeadAlgorithm enc = Jwts.ENC.A128CBC_HS256;
                SecretKey secretKey = KeyToolGenerator.convertStringKeyToSecretKey(
                        key.getSecred_key(), 
                        key.getId_encoding_scheme()
                    );
               
                String jwe = Jwts.builder()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .encryptWith(secretKey, enc)
                        .compact();
                
                token = new Token(jwe, null, 10000000L, autorisasi.getId());
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
                        .header().keyId(
                            KeyToolGenerator.byteArrayToHexString(publicKey.getEncoded())
                        )
                        .add("typ", "JWT").and()
                        .issuer("DLHK Sidoarjo")
                        .subject("sikoling")
                        .audience().add(autorisasi.getUser_name()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .encryptWith(publicKey, alg, enc)
                        .compact();
                
                token = new Token(jwe, null, 10000000L, autorisasi.getId());
            }
            default -> throw new AssertionError();
        }   

        
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
        Jws<Claims> jws;
        
        try {
            jws = (Jws<Claims>) Jwts.parser();
//            .keyLocator(keyLocator)
//            .verifyWith(null)
//            .build()
//            .parseSignedClaims(jwsString);
//            jws.getPayload().getId();
            return jws.getPayload();
        } catch (JwtException e) {
            return null;
        }
    }
}
