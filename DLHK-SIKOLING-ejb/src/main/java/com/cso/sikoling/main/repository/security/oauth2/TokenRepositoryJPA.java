package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.RepositoryToken;
import com.cso.sikoling.main.repository.security.AutorisasiData;
import com.cso.sikoling.main.repository.security.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.persistence.EntityManager;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset(); 
            messageDigest.update(c.getPassword().getBytes());
            byte[] digest2 = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            String hx2;
            for (int i=0;i<digest2.length;i++){
                hx2 = Integer.toHexString(0xFF & digest2[i]);
                if(hx2.length() == 1){
                    hx2 = "0" + hx2;
                } 
                sb.append(hx2);
            }
            
            String messageDigestPassword = sb.toString();
            
            UserData userData = entityManager.createQuery("SELECT u FROM UserData u WHERE u.userName = :user AND u.password = :password", UserData.class)
                            .setParameter("user", c.getEmail())
                            .setParameter("password", messageDigestPassword)
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
                        .audience().add(autorisasiData.getHakAkses().getId()).and()
                        .expiration(nextYear)
                        .issuedAt(today)
                        .id(autorisasiData.getId())
                        .signWith(key)
                        .compact();
            Token token = new Token(jwt, jwt, 10000000L, autorisasiData.getId());
            
            return token;
        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(TokenRepositoryJPA.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (Exception e) {
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

    @Override
    public void generateSecretKey(String signatureAlgoritma) {
        switch (signatureAlgoritma) {
            case "HS512" -> {
                MacAlgorithm alg = Jwts.SIG.HS512; 
                SecretKey key = alg.key().build();
            }
            case "HS384" -> {
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey key = alg.key().build();
            }
            case "HS256" -> {
                MacAlgorithm alg = Jwts.SIG.HS256; 
                SecretKey key = alg.key().build();
            }
            case "RS512" -> {
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build();
            }
            case "RS384" -> {
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
            }
            case "RS256" -> {
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
            }
            case "PS512" -> {
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
            }
            case "PS384" -> {
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
            }
            case "PS256" -> {
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
            }
            case "ES512" -> {
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
            }
            case "ES384" -> {
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
            }
            case "ES256" -> {
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
            }
            case "Ed25519" -> {
                Curve curve = Jwks.CRV.Ed25519; 
                KeyPair pair = curve.keyPair().build();
            }
            case "Ed448" -> {
                Curve curve = Jwks.CRV.Ed448; 
                KeyPair pair = curve.keyPair().build();
            }
            default -> throw new AssertionError();
        }
    }

}
