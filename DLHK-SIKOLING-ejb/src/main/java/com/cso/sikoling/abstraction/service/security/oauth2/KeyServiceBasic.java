package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.repository.Repository;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikoling.main.util.oauth2.KeyConverter;


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
                String secretKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "02" -> {   
                MacAlgorithm alg = Jwts.SIG.HS384; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "03" -> {   
                MacAlgorithm alg = Jwts.SIG.HS512; 
                SecretKey secretKey = alg.key().build();
                String secretKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(secretKey.getEncoded(), encodingScheme);                
                key = new Key(id, idRealm, idJwa, encodingScheme, secretKeyWithEncodingScheme);
            }
            case "04" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme); 
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());                
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "05" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "06" -> {   
                SignatureAlgorithm alg = Jwts.SIG.RS512; 
                KeyPair pair = alg.keyPair().build(); 
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "07" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "08" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "09" -> {   
                SignatureAlgorithm alg = Jwts.SIG.ES512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "10" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS256; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "11" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS384; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "12" -> {   
                SignatureAlgorithm alg = Jwts.SIG.PS512; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            case "36" -> { 
                SignatureAlgorithm alg = Jwts.SIG.EdDSA; 
                KeyPair pair = alg.keyPair().build();
                
                PublicKey publicKey = pair.getPublic();
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
                String publicKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(x509EncodedKeySpec.getEncoded(), encodingScheme);
                
                PrivateKey privateKey = pair.getPrivate();
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                String privateKeyWithEncodingScheme = KeyConverter.convertBinaryKeyToStringKey(pkcs8EncodedKeySpec.getEncoded(), encodingScheme);
                
                key = new Key(id, idRealm, idJwa, encodingScheme, privateKeyWithEncodingScheme, publicKeyWithEncodingScheme);
            }
            default -> throw new AssertionError();
        }
        
        return key;
    }
    
}
