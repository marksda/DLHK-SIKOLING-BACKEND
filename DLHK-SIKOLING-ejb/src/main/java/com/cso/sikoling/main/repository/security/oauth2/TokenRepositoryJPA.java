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
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.InvalidKeyException;
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
import javax.crypto.spec.SecretKeySpec;


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
    public String generateSecretKey(String signatureAlgoritma) {
        String generatedKey = null;
        
        switch (signatureAlgoritma) {
            case "HS512" -> {   
                MacAlgorithm alg = Jwts.SIG.HS512; 
                SecretKey key = alg.key().build();       
                generatedKey = convertSecretKeyToString(key.getEncoded());
            }
            case "HS384" -> {   
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey key = alg.key().build();
                generatedKey = convertSecretKeyToString(key.getEncoded());
            }
            case "HS256" -> {   
                MacAlgorithm alg = Jwts.SIG.HS256; 
                SecretKey key = alg.key().build();
                generatedKey = convertSecretKeyToString(key.getEncoded());
            }
            case "RS512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "RS384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "RS256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "PS512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "PS384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "PS256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "ES512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "ES384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "ES256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            case "EdDSA" -> { 
                SignatureAlgorithm alg = Jwts.SIG.EdDSA; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertSecretKeyToString(pair.getPublic().getEncoded());
            }
            default -> throw new AssertionError();
        }
        
        return generatedKey;
    }
    
    private String convertSecretKeyToString(byte[]secretKey) {
        String encodedKey = Encoders.BASE64URL.encode(secretKey); 
        return encodedKey;
    }
    
    private SecretKey convertStringToSecretKey(String encodedKey, String signatureAlgoritma) {
        byte[] decodedKey = Decoders.BASE64URL.decode(encodedKey);
        SecretKey originalKey = null; 
        switch (signatureAlgoritma) {
            case "HS512" -> {   
                originalKey = new SecretKeySpec(decodedKey, "HmacSHA512");               
            }
            case "HS384" -> {  
                originalKey = new SecretKeySpec(decodedKey, "HmacSHA384");
            }
            case "HS256" -> {  
                originalKey = new SecretKeySpec(decodedKey, "HmacSHA256");
            }
            case "RS512" -> {  
                originalKey = new SecretKeySpec(decodedKey, "SHA512withRSA");
            }
            case "RS384" -> {   
                originalKey = new SecretKeySpec(decodedKey, "SHA384withRSA");
            }
            case "RS256" -> {   
                originalKey = new SecretKeySpec(decodedKey, "SHA256withRSA");
            }
            case "PS512" -> {
                originalKey = new SecretKeySpec(decodedKey, "RSASSA-PSS");
            }
            case "PS384" -> { 
                originalKey = new SecretKeySpec(decodedKey, "RSASSA-PSS");
            }
            case "PS256" -> {  
                originalKey = new SecretKeySpec(decodedKey, "RSASSA-PSS");
            }
            case "ES512" -> {   
                originalKey = new SecretKeySpec(decodedKey, "SHA512withECDSA");
            }
            case "ES384" -> {   
                originalKey = new SecretKeySpec(decodedKey, "SHA384withECDSA");
            }
            case "ES256" -> {   
                originalKey = new SecretKeySpec(decodedKey, "SHA256withECDSA");
            }
            case "EdDSA" -> { 
                originalKey = new SecretKeySpec(decodedKey, "EdDSA");
            }
            default -> throw new AssertionError();
        }
        
        return originalKey;
    }

}
