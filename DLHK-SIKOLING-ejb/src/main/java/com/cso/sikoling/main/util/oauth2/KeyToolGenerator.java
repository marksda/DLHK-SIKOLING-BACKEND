package com.cso.sikoling.main.util.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.EncodingScheme;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.Jwks;
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
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public final class KeyToolGenerator {
    
    public static String byteArrayToHexString(byte[] arrayOfByte) {
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
    
    public static byte[] hexStringToByteArrayTo(String hexString) {
        byte[] ans = new byte[hexString.length() / 2];
        for (int i = 0; i < ans.length; i++) {
            int index = i * 2;
            int val = Integer.parseInt(hexString.substring(index, index + 2), 16);
            ans[i] = (byte)val;
        }
        
        return ans;
    }
    
    public static String convertBinaryKeyToStringKey(byte[] key, String encodingScheme) {
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
    
    public static SecretKey convertStringKeyToHmacSecretKey(String stringKey, String encodingScheme) {
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
    
    public static SecretKey convertStringKeyToAESSecretKey(String stringKey, String encodingScheme) {
        SecretKey key = null; 
        
        switch (encodingScheme) {
            case "00" -> {  
                key = new SecretKeySpec(Decoders.BASE64.decode(stringKey), "AES");
            }
            case "01" -> {   
                key = new SecretKeySpec(Decoders.BASE64URL.decode(stringKey), "AES");
            }
            case "02" -> {   
                key = new SecretKeySpec(hexStringToByteArrayTo(stringKey), "AES");
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
    public static PublicKey convertStringKeyToPublicKey(String stringPublicKey, String idJwa, String encodingScheme) {
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
            case "04" -> {   
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
            case "06" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
            case "08" -> {   
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("EC");
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
            case "10" -> {  
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
            case "12" -> {
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSASSA-PSS");
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
            case "19" -> {  
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    publicKey = keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            default -> throw new AssertionError();
        }
        
        return publicKey;
    }
    
    public static PrivateKey convertStringKeyToPrivateKey(String stringPrivateKey, String idJwa, String encodingScheme) {
        
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
                    KeyFactory keyFactory = KeyFactory.getInstance("Ed448");    //or Ed25519
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
            }
            case "19" -> {  
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    privateKey = keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new AssertionError();
                }
                
            }
            default -> throw new AssertionError();
        }
        
        return privateKey;
    }
    
    public static Key generateKey(String idRealm, String idJwa, String encodingScheme) {
        UUID uuid = UuidCreator.getTimeOrderedEpoch();
        String id = uuid.toString();
        Key key = null;
        Date timeStamp = new Date();
        Realm realm = new Realm(idRealm, null);
        EncodingScheme encoding = new EncodingScheme(encodingScheme, null);
        Jwa jwa = new Jwa(idJwa, null, null, null);
        
        switch (idJwa) {
            case "01" -> {   
                MacAlgorithm alg = Jwts.SIG.HS256; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);                
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "02" -> {   
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "03" -> {   
                MacAlgorithm alg = Jwts.SIG.HS512; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);                
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "04" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme); 
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());                
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "05" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "06" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "07" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "08" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "09" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "10" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "11" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "12" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "36" -> { 
                Curve curve = Jwks.CRV.Ed448;  //or Ed25519
                KeyPair pair = curve.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            case "13" -> {  
                AeadAlgorithm enc = Jwts.ENC.A128CBC_HS256;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "14" -> {  
                AeadAlgorithm enc = Jwts.ENC.A192CBC_HS384;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "15" -> {  
                AeadAlgorithm enc = Jwts.ENC.A256CBC_HS512;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "16" -> {  
                AeadAlgorithm enc = Jwts.ENC.A128GCM;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "17" -> {  
                AeadAlgorithm enc = Jwts.ENC.A192GCM;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "18" -> {  
                AeadAlgorithm enc = Jwts.ENC.A256GCM;
                SecretKey secretKey = enc.key().build();
                String secretKeyWithEncodingScheme = convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, realm, jwa, encoding, secretKeyWithEncodingScheme, timeStamp);
            }
            case "19" -> {
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, realm, jwa, encoding, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme, timeStamp);
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }

}
