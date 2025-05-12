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
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.persistence.EntityManager;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
            
            SecretKey key = convertStringToSecretKey(
                    Decoders.BASE64URL.decode(generateSecretKey("HS256")), 
                    "HS256"
            );         
            
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
        } catch (NoSuchAlgorithmException | InvalidKeyException | DecodingException ex) {
            // Logger.getLogger(TokenRepositoryJPA.class.getName()).log(Level.SEVERE, null, ex);
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
                generatedKey = convertKeyToString(key.getEncoded(), "BASE64URL");
            }
            case "HS384" -> {   
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey key = alg.key().build();
                generatedKey = convertKeyToString(key.getEncoded(), "BASE64URL");
            }
            case "HS256" -> {   
                MacAlgorithm alg = Jwts.SIG.HS256; 
                SecretKey key = alg.key().build();
                generatedKey = convertKeyToString(key.getEncoded(), "BASE64URL");
            }
            case "RS512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "RS384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "RS256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "PS512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "PS384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "PS256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "ES512" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "ES384" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "ES256" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            case "EdDSA" -> { 
                SignatureAlgorithm alg = Jwts.SIG.EdDSA; 
                KeyPair pair = alg.keyPair().build();
                generatedKey = convertKeyToString(pair.getPublic().getEncoded(), "BASE64URL");
            }
            default -> throw new AssertionError();
        }
        
        return generatedKey;
    }
    
    private String convertKeyToString(byte[]secretKey, String encodeType) {
        String encodedKey = null;
        
        switch (encodeType) {
            case "BASE64URL" -> {   
                encodedKey = Encoders.BASE64URL.encode(secretKey); 
            }
            case "BASE64" -> {  
                encodedKey = Encoders.BASE64.encode(secretKey); 
            }
            default -> throw new AssertionError();
        }        
        
        return encodedKey;
    }
    
    private SecretKey convertStringToSecretKey(byte[] decodedKey, String signatureAlgoritma) {
        SecretKey key = null; 
        switch (signatureAlgoritma) {
            case "HS512" -> {   
                key = Keys.hmacShaKeyFor(decodedKey);
            }
            case "HS384" -> {  
                key = Keys.hmacShaKeyFor(decodedKey);
            }
            case "HS256" -> {  
                key = Keys.hmacShaKeyFor(decodedKey);
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
    private PublicKey convertStringToPublicKey(byte[] decodedKey, String signatureAlgoritma) {
        PublicKey key = null; 
        switch (signatureAlgoritma) {            
            case "RS512" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            case "RS384" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "RS256" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS512" -> {
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS384" -> { 
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS256" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES512" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES384" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES256" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "EdDSA" -> { 
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
    private PrivateKey convertStringToPrivateKey(byte[] decodedKey, String signatureAlgoritma) {
        PrivateKey key = null; 
        switch (signatureAlgoritma) {            
            case "RS512" -> {  
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            case "RS384" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "RS256" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS512" -> {
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS384" -> { 
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "PS256" -> {  
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES512" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES384" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "ES256" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "EdDSA" -> { 
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    key = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }

}
