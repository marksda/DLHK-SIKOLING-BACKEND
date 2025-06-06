package com.cso.sikoling.main.util.oauth2;

import com.password4j.Argon2Function;
import com.password4j.BcryptFunction;
import com.password4j.CompressedPBKDF2Function;
import com.password4j.Hash;
import com.password4j.PBKDF2Function;
import com.password4j.Password;
import com.password4j.PepperGenerator;
import com.password4j.ScryptFunction;
import com.password4j.types.Argon2;
import com.password4j.types.Bcrypt;
import static com.password4j.types.Bcrypt.A;
import static com.password4j.types.Bcrypt.B;
import static com.password4j.types.Bcrypt.X;
import static com.password4j.types.Bcrypt.Y;
import com.password4j.types.Hmac;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class PasswordHasher {
    
    public static String getMDSHA256Crypt(String plainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainTextPassword.getBytes());
            return KeyToolGenerator.byteArrayToHexString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static boolean checkMDSHA256Crypt(String plainTextPassword, String hashTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainTextPassword.getBytes());
            String messageDigestPassword = KeyToolGenerator.byteArrayToHexString(encodedHash);
            
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
                                .addPepper(PepperGenerator.get())
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
                                .addPepper(PepperGenerator.get())
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
                                .addPepper(PepperGenerator.get())
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
                                .addPepper(PepperGenerator.get())
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
    
    public static boolean checkBcrypt(String plainTextPassword, String hashTextPassword, String pepper) {
        BcryptFunction bcrypt = BcryptFunction.getInstanceFromHash(hashTextPassword);
        boolean verified;
        
        if(pepper != null) {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(pepper)
                        .with(bcrypt);
        }
        else {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(PepperGenerator.get())
                        .with(bcrypt);
        }
        
        return verified;
    }
    
    public static String getScrypt(String plainTextPassword, int workFactor,
            int memoryBlockSize, int countOfParallelisation, int outputLength,
                byte[] salt, String pepper) {
        
        ScryptFunction scrypt = ScryptFunction.getInstance(
                workFactor, memoryBlockSize, 
                countOfParallelisation, outputLength
            );
        
        if(salt != null) {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(pepper)
                            .addSalt(salt)
                            .with(scrypt);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(PepperGenerator.get())
                            .addSalt(salt)
                            .with(scrypt);
                
                return hash.getResult();
            }            
        }
        else {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(pepper)
                            .with(scrypt);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(PepperGenerator.get())
                            .with(scrypt);
                
                return hash.getResult();
            }
        }
    }
    
    public static boolean checkScrypt(String plainTextPassword, String hashTextPassword,  String pepper) {
        
        ScryptFunction scrypt = ScryptFunction.getInstanceFromHash(hashTextPassword);
        boolean verified;
        
        if(pepper != null) {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(pepper)
                        .with(scrypt);
        }
        else {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(PepperGenerator.get())
                        .with(scrypt);
        }
        
        return verified;
    }
    
    public static String getArgon2(String plainTextPassword, int amountOfMemory, 
            int numberOfIteration, int countOfParallelisation, int outputLength, 
            Argon2 argon2TypeAlg, int version, byte[] salt, String pepper) {
        
        Argon2Function argon2 = Argon2Function.getInstance(
                amountOfMemory, numberOfIteration, outputLength, 
                outputLength, argon2TypeAlg, version);
        
        if(salt != null) {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper(pepper)
                    .addSalt(salt)
                    .with(argon2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(PepperGenerator.get())
                            .addSalt(salt)
                            .with(argon2);
                
                return hash.getResult();
            }
        }
        else {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(pepper)
                            .with(argon2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                            .addPepper(PepperGenerator.get())
                            .with(argon2);
                
                return hash.getResult();
            }
        }
        
    }
    
    public static boolean checkArgon2(String plainTextPassword, 
                                        String hashTextPassword, String pepper) {
        
        Argon2Function argon2 = Argon2Function.getInstanceFromHash(hashTextPassword);
        boolean verified;
        
        if(pepper != null) {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(pepper).with(argon2);
        }
        else {
            verified = Password.check(plainTextPassword, hashTextPassword)
                        .addPepper(PepperGenerator.get()).with(argon2);
        }
        
        return verified;
    }
    
    public static String getPBKDF2(String plainTextPassword, int prfIteration, 
                    int outputLength, Hmac algType, String salt, String pepper) {
        
        PBKDF2Function pbkdf2 = PBKDF2Function.getInstance(algType, prfIteration, outputLength);
        
        if(salt != null) {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper(pepper)
                    .addSalt(salt)
                    .with(pbkdf2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper(PepperGenerator.get())
                    .addSalt(salt)
                    .with(pbkdf2);
                
                return hash.getResult();
            }
        }
        else {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                        .addPepper(pepper)
                        .with(pbkdf2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                        .addPepper(PepperGenerator.get())
                        .with(pbkdf2);
                
                return hash.getResult();
            }
        }
        
    }
    
    public static boolean checkPBKDF2(String plainTextPassword, String hashTextPassword, 
            int prfIteration, int outputLength, Hmac algType, String salt, String pepper) {
        
        PBKDF2Function pbkdf2 = PBKDF2Function.getInstance(algType, prfIteration, outputLength);        
        boolean verified;
        
        if(salt != null) {
            if(pepper != null) {
                verified = Password.check(plainTextPassword, hashTextPassword)
                           .addPepper(pepper)
                           .addSalt(salt)
                           .with(pbkdf2);
            }
            else {
                verified = Password.check(plainTextPassword, hashTextPassword)
                            .addPepper(PepperGenerator.get())
                           .addSalt(salt)
                           .with(pbkdf2);
            }
        }
        else {
            if(pepper != null) {
                verified = Password.check(plainTextPassword, hashTextPassword)
                           .addPepper(pepper)
                           .with(pbkdf2);
            }
            else {
                verified = Password.check(plainTextPassword, hashTextPassword)
                           .addPepper(PepperGenerator.get())
                           .with(pbkdf2);
            }
        }
        
        return verified;
    }
    
    public static String getCompressedPBKDF2(String plainTextPassword, int prfIteration, 
            int outputLength, Hmac algType, byte[] salt, String pepper) {
        
        CompressedPBKDF2Function compressedPBKDF2 = CompressedPBKDF2Function
                .getInstance(algType, prfIteration, outputLength);
        
        if(salt != null) {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper(pepper)
                    .addSalt(salt)
                    .with(compressedPBKDF2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                    .addPepper(PepperGenerator.get())
                    .addSalt(salt)
                    .with(compressedPBKDF2);
                
                return hash.getResult();
            }
        }
        else {
            if(pepper != null) {
                Hash hash = Password.hash(plainTextPassword)
                        .addPepper(pepper)
                        .addRandomSalt()
                        .with(compressedPBKDF2);
                
                return hash.getResult();
            }
            else {
                Hash hash = Password.hash(plainTextPassword)
                        .addPepper(PepperGenerator.get())
                        .addRandomSalt()
                        .with(compressedPBKDF2);
                
                return hash.getResult();
            }
        }
        
    }
    
    public static boolean checkCompressedPBKDF2(String plainTextPassword, 
            String hashTextPassword, String pepper) {
        String regex = "\\$";
        String[] parts = hashTextPassword.split(regex);
        String salt = parts[3];
        
        CompressedPBKDF2Function compressedPBKDF2 = CompressedPBKDF2Function.getInstanceFromHash(hashTextPassword);
        
        boolean verified;
        
        if(salt != null) {
            if(pepper != null) {
                verified = Password.check(plainTextPassword, hashTextPassword)
                            .addPepper(pepper)
                            .addSalt(salt)
                            .with(compressedPBKDF2);
            }        
            else {
                verified = Password.check(plainTextPassword, hashTextPassword)
                            .addPepper(PepperGenerator.get())
                            .addSalt(salt)
                            .with(compressedPBKDF2);
            }
        }
        else {            
            if(pepper != null) {
                verified = Password.check(plainTextPassword, hashTextPassword)
                            .addPepper(pepper)
                            .with(compressedPBKDF2);
            }        
            else {
                verified = Password.check(plainTextPassword, hashTextPassword)
                            .addPepper(PepperGenerator.get())
                            .with(compressedPBKDF2);
            }
        }
        
        return verified;
    }
    
}
