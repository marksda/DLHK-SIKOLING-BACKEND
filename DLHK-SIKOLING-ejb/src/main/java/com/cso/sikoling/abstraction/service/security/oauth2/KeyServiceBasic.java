package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.repository.Repository;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import com.cso.sikoling.abstraction.service.KeyService;


public class KeyServiceBasic implements KeyService<Key> {
    
    private final Repository<Key, QueryParamFilters, Filter> repository;

    public KeyServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Key save(Key t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Key update(Key t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Key updateId(String idLama, Key t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Key> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }
    
    @Override
    public Key generateKey(String idRealm, String idJwa, String encodingScheme) {
        UUID uuid = UuidCreator.getTimeOrderedEpoch();
        String id = uuid.toString();
        Key key = null;
        
        switch (idJwa) {
            case "01" -> {   
                MacAlgorithm alg = Jwts.SIG.HS256; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertKeyToString(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "02" -> {   
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertKeyToString(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "03" -> {   
                MacAlgorithm alg = Jwts.SIG.HS512; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertKeyToString(secretKey.getEncoded(), encodingScheme);                
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "04" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme); 
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());                
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "05" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "06" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "07" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "08" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "09" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "10" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "11" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "12" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "36" -> { 
                SignatureAlgorithm alg = Jwts.SIG.EdDSA; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertKeyToString(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertKeyToString(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
    private String byteArrayToHexString(byte[] arrayOfByte) {
        StringBuilder sb = new StringBuilder();
        String hx2;
        for (int i=0;i<arrayOfByte.length;i++){
            hx2 = Integer.toHexString(0xFF & arrayOfByte[i]);
            if(hx2.length() == 1){
                hx2 = "0" + hx2;
            } 
            sb.append(hx2);
        }

        return sb.toString();
    }
    
    private byte[] hexStringToByteArrayTo(String hexString) {
        byte[] ans = new byte[hexString.length() / 2];
        for (int i = 0; i < ans.length; i++) {
            int index = i * 2;
            int val = Integer.parseInt(hexString.substring(index, index + 2), 16);
            ans[i] = (byte)val;
        }
        
        return ans;
    }
    
    private String convertKeyToString(byte[] key, String encodingScheme) {
        String encodedKey = null;
        
        switch (encodingScheme) {
            case "00" -> {  
                encodedKey = Encoders.BASE64.encode(key); 
            }
            case "01" -> {   
                encodedKey = Encoders.BASE64URL.encode(key); 
            }
            case "02" -> {   
                encodedKey = byteArrayToHexString(key); 
            }
            default -> throw new AssertionError();
        }        
        
        return encodedKey;
    }
    
    private SecretKey convertStringToSecretKey(String stringKey, String encodingScheme) {
        SecretKey key = null; 
        
        switch (encodingScheme) {
            case "00" -> {  
                key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(stringKey));
            }
            case "01" -> {   
                key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(stringKey));
            }
            case "02" -> {   
                key = Keys.hmacShaKeyFor(hexStringToByteArrayTo(stringKey));
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
    private PublicKey convertStringToPublicKey(String stringPublicKey, String idJwa, String encodingScheme) {
        byte[] decodedKey;
        PublicKey publicKey = null; 
        
        switch (encodingScheme) {
            case "00" -> {
                decodedKey = Decoders.BASE64.decode(stringPublicKey);
            }    
            case "01" -> {
                decodedKey = Decoders.BASE64URL.decode(stringPublicKey);
            }
            case "02" -> {
                decodedKey = hexStringToByteArrayTo(stringPublicKey);
            }
            default -> throw new AssertionError();
        }
        
        switch (idJwa) {            
            case "06" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            case "05" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "04" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "12" -> {
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "11" -> { 
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "10" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "09" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "08" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "07" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "36" -> { 
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            default -> throw new AssertionError();
        }
        
        return publicKey;
    }
    
    private PrivateKey convertStringToPrivateKey(String stringPrivateKey, String idJwa, String encodingScheme) {
        
        byte[] decodedKey;
        PrivateKey privateKey = null;
        
        switch (encodingScheme) {
            case "00" -> {
                decodedKey = Decoders.BASE64.decode(stringPrivateKey);
            }    
            case "01" -> {
                decodedKey = Decoders.BASE64URL.decode(stringPrivateKey);
            }
            case "02" -> {
                decodedKey = hexStringToByteArrayTo(stringPrivateKey);
            }
            default -> throw new AssertionError();
        }
                            
         
        switch (idJwa) {            
            case "06" -> {  
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            case "05" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "04" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "12" -> {
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "11" -> { 
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "10" -> {  
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "09" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "08" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "07" -> {   
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "36" -> { 
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            default -> throw new AssertionError();
        }
        
        return privateKey;
    }

}
