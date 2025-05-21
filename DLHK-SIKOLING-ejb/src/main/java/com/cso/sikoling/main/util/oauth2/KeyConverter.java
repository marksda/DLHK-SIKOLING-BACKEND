package com.cso.sikoling.main.util.oauth2;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.SecretKey;


public final class KeyConverter {
    
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
    
    public static SecretKey convertStringKeyToSecretKey(String stringKey, String encodingScheme) {
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
