package com.cso.sikoling.main.util.oauth2;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.ScryptFunction;
import com.password4j.types.Bcrypt;
import static com.password4j.types.Bcrypt.A;
import static com.password4j.types.Bcrypt.B;
import static com.password4j.types.Bcrypt.X;
import static com.password4j.types.Bcrypt.Y;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class PasswordHasher {
    
    public static String getMDSHA256Crypt(String plainTextPassword) {
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
    
    public static String getBcrypt(String plainTextPassword, Bcrypt bcryptVersion, 
                String pepper, int costFactor) {
        
        switch (bcryptVersion) {
            case A -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.A, costFactor);
                Hash hash = pepper == null ?
                        Password.hash(plainTextPassword)
                                .with(bcrypt)
                        : Password.hash(plainTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
                
                return hash.getResult();
            }  
            case B -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, costFactor);
                Hash hash = pepper == null ?
                        Password.hash(plainTextPassword)
                                .with(bcrypt)
                        : Password.hash(plainTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
                
                return hash.getResult();
            }  
            case X -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                Hash hash = pepper == null ?
                        Password.hash(plainTextPassword)
                                .with(bcrypt)
                        : Password.hash(plainTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
                
                return hash.getResult();
            }  
            case Y -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.Y, costFactor);
                Hash hash = pepper == null ?
                        Password.hash(plainTextPassword)
                                .with(bcrypt)
                        : Password.hash(plainTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
                
                return hash.getResult();
            }  
            default -> {
                return null;
            }
        }
        
    } 
    
    public static boolean checkBcrypt(String plainTextPassword, String hashTextPassword, 
            Bcrypt bcryptVersion, String pepper, int costFactor) {
        switch (bcryptVersion) {
            case A -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.A, costFactor);
                
                return pepper == null ?
                        Password.check(plainTextPassword, hashTextPassword)
                                .with(bcrypt)
                        :Password.check(plainTextPassword, hashTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
            }  
            case B -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, costFactor);
                
                return pepper == null ?
                        Password.check(plainTextPassword, hashTextPassword)
                                .with(bcrypt)
                        :Password.check(plainTextPassword, hashTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
            }  
            case X -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.X, costFactor);
                
                return pepper == null ?
                        Password.check(plainTextPassword, hashTextPassword)
                                .with(bcrypt)
                        :Password.check(plainTextPassword, hashTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
            }  
            case Y -> {
                BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.Y, costFactor);
                
                return pepper == null ?
                        Password.check(plainTextPassword, hashTextPassword)
                                .with(bcrypt)
                        :Password.check(plainTextPassword, hashTextPassword)
                                .addPepper(pepper)
                                .with(bcrypt);
            }  
            default -> {
                return false;
            }
        }
    }
    
    public static String getScrypt(String plainTextPassword, int workFactor,
            int memoryBlockSize, int countOfParallelisation, int outputLength,
                String salt, String pepper) {
        
        ScryptFunction scrypt = ScryptFunction.getInstance(
                workFactor, memoryBlockSize, 
                countOfParallelisation, outputLength
            );
        
        if(salt != null) {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper("pepper")
                    .addSalt("salt")
                    .with(scrypt);
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper("pepper")
                    .with(scrypt);
                return hash.getResult();
            }            
        }
        else {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper("pepper")
                    .with(scrypt);
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                    .with(scrypt);
                return hash.getResult();
            }
        }
    }
    
    public static boolean checkScrypt(String plainTextPassword, String hashTextPassword,
            String pepper) {
        
        ScryptFunction scrypt = ScryptFunction.getInstanceFromHash(hashTextPassword);
        
        boolean verified = Password.check(plainTextPassword, hashTextPassword)
                           .addPepper(pepper)
                           .with(scrypt);
        
        return verified;
    }
    
}
