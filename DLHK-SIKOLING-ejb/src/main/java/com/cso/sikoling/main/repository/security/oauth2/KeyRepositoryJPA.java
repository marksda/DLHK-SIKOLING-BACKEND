package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.repository.KeyRepository;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;


public class KeyRepositoryJPA implements KeyRepository<Key, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KeyRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Key save(Key t) throws SQLException {   
        try {
            KeyData keyData = convertKeyToKeyData(t);
            entityManager.persist(keyData);
            entityManager.flush();             
            return convertKeyDataToKey(keyData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id key harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data key");
        }        
    }

    @Override
    public Key update(Key t) throws SQLException {
        
        try {
            KeyData keyData = convertKeyToKeyData(t);  
            keyData = entityManager.merge(keyData);
            return convertKeyDataToKey(keyData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id key harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data key");
        }
        
    }

    @Override
    public Key updateId(String idLama, Key t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KeyData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id key");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id key harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id key");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KeyData keyData = entityManager.find(KeyData.class, id);
            if(keyData != null) {
                entityManager.remove(keyData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("key dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id key harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Key> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KeyData> cq = cb.createQuery(KeyData.class);
            Root<KeyData> root = cq.from(KeyData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        default -> {
                        }
                    }			
                }

                if(daftarPredicate.isEmpty()) {
                    cq.select(root);
                }
                else {
                    cq.select(root).where(cb.and(daftarPredicate.toArray(new Predicate[0])));
                }
            }        

            // sort clause
            if(q.getFields_sorter() != null) {
                Iterator<SortOrder> iterSort = q.getFields_sorter().iterator();
                while (iterSort.hasNext()) {
                    SortOrder sort = (SortOrder) iterSort.next();
                    switch (sort.getField_name()) {
                        case "id" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("id")));
                            }
                        }
                        case "nama" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("nama")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KeyData> typedQuery;	

            if( q.getIs_paging()) { 
                Paging paging = q.getPaging();
                typedQuery = entityManager.createQuery(cq)
                                .setMaxResults(paging.getPage_size())
                                .setFirstResult((paging.getPage_number()-1)*paging.getPage_size());
            }
            else {
                typedQuery = entityManager.createQuery(cq);
            }

            return typedQuery.getResultList()
                            .stream()
                            .map(d -> convertKeyDataToKey(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KeyData.findAll", KeyData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKeyDataToKey(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KeyData> root = cq.from(KeyData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                default -> {
                }
            }			
        }

        if(daftarPredicate.isEmpty()) {
            cq.select(cb.count(root));
        }
        else {
            cq.select(cb.count(root)).where(cb.and(daftarPredicate.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(cq).getSingleResult();
        
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
    
    private Key convertKeyDataToKey(KeyData d) {
        Key key = null;
		
        if(d != null) {
            key =  new Key(
                    d.getId(), d.getJwa().getId(), 
                    d.getRealm().getId(), d.getEncoding_scheme().getId(),
                    d.getSecretKey(), d.getPrivateKey(), d.getPublicKey()
                );
        }

        return key;	
    }
    
    private KeyData convertKeyToKeyData(Key t) {
        KeyData keyData = null;
		
        if(t != null) {
            keyData = new KeyData();
            
            if(t.getId() != null) {
                keyData.setId(t.getId());
            }
            else {
               UUID uuid = UuidCreator.getTimeOrderedEpoch();
               keyData.setId(uuid.toString()); 
            }
            
            RealmData realmData = new RealmData(t.getId_realm());
            keyData.setRealm(realmData);
            JwaData jwaData = new JwaData(t.getId_jwa());
            keyData.setJwa(jwaData);
            EncodingSchemaData encodingSchemaData = new EncodingSchemaData(t.getId_encoding_scheme());
            keyData.setEncoding_scheme(encodingSchemaData);
            keyData.setSecretKey(t.getSecred_key());
            keyData.setPrivateKey(t.getPrivate_key());
            keyData.setPublicKey(t.getPublic_key());
        }

        return keyData;
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
