package com.cso.sikoling.main.util.oauth2;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import static com.password4j.types.Bcrypt.A;
import static com.password4j.types.Bcrypt.B;
import static com.password4j.types.Bcrypt.X;
import static com.password4j.types.Bcrypt.Y;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class PasswordHasher {
    
    public static String getMDHA256Crypt(String plainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainTextPassword.getBytes());
            return KeyConverter.byteArrayToHexString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static boolean checkMDSHA256Crypt(String plainTextPassword, String hashTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainTextPassword.getBytes());
            String messageDigestPassword = KeyConverter.byteArrayToHexString(encodedHash);
            
            return messageDigestPassword.equals(hashTextPassword);            
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
    
    public static String getHashBcrypt(String plainTextPassword,  
                                    Bcrypt bcryptVersion, int costFactor) {
        
        switch (bcryptVersion) {
            case A -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.A, costFactor);
                Hash hash = Password.hash(plainTextPassword).with(bcrypt);
                return hash.getResult();
            }  
            case B -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, costFactor);
                Hash hash = Password.hash(plainTextPassword).with(bcrypt);
                return hash.getResult();
            }  
            case X -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                Hash hash = Password.hash(plainTextPassword).with(bcrypt);
                return hash.getResult();
            }  
            case Y -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                Hash hash = Password.hash(plainTextPassword).with(bcrypt);
                return hash.getResult();
            }  
            default -> {
                return null;
            }
        }
        
    } 
    
    public static boolean checkHashBcrypt(String plainTextPassword,  
            String hashTextPassword, Bcrypt bcryptVersion, int costFactor) {
        switch (bcryptVersion) {
            case A -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.A, costFactor);
                return Password.check(plainTextPassword, hashTextPassword).with(bcrypt);
            }  
            case B -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, costFactor);
                return Password.check(plainTextPassword, hashTextPassword).with(bcrypt);
            }  
            case X -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                return Password.check(plainTextPassword, hashTextPassword).with(bcrypt);
            }  
            case Y -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                return Password.check(plainTextPassword, hashTextPassword).with(bcrypt);
            }  
            default -> {
                return false;
            }
        }
    }
    
}
