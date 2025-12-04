package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.EncodingScheme;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.cso.sikoling.abstraction.repository.Repository;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class KeyRepositoryJPA implements Repository<Key, QueryParamFilters, Filter> {
    
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
                        case "id_realm" -> daftarPredicate.add(cb.equal(root.get("realm").get("id"), filter.getValue()));
                        case "id_jwa" -> daftarPredicate.add(cb.equal(root.get("jwa").get("id"), filter.getValue()));
                        case "tanggal" -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDate targetDate = LocalDate.parse(filter.getValue(), formatter);
                            LocalDateTime startOfDay = targetDate.atStartOfDay();
                            LocalDateTime startOfNextDay = targetDate.plusDays(1).atStartOfDay();
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggal"), 
                                    startOfDay, 
                                    startOfNextDay
                                )
                            );                            
                        }
                        case "rentang_tanggal" -> {
                            Jsonb jsonb = JsonbBuilder.create();
                            JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");       
                            LocalDate fromDate = LocalDate.parse(d.getString("from"), formatter);
                            LocalDateTime fromDateTime = fromDate.atStartOfDay();
                            LocalDate toDate = LocalDate.parse(d.getString("to"), formatter);
                            LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggal"), 
                                    fromDateTime, 
                                    toDateTime
                                )
                            );
                        }
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
                        case "realm" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("realm").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("realm").get("id")));
                            }
                        }
                        case "jwa" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("jwa").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("jwa").get("id")));
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
                case "id_realm" -> daftarPredicate.add(cb.equal(root.get("realm").get("id"), filter.getValue()));
                case "id_jwa" -> daftarPredicate.add(cb.equal(root.get("jwa").get("id"), filter.getValue()));
                case "tanggal" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDate targetDate = LocalDate.parse(filter.getValue(), formatter);
                    LocalDateTime startOfDay = targetDate.atStartOfDay();
                    LocalDateTime startOfNextDay = targetDate.plusDays(1).atStartOfDay();

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggal"), 
                            startOfDay, 
                            startOfNextDay
                        )
                    );                            
                }
                case "rentang_tanggal" -> {
                    Jsonb jsonb = JsonbBuilder.create();
                    JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");       
                    LocalDate fromDate = LocalDate.parse(d.getString("from"), formatter);
                    LocalDateTime fromDateTime = fromDate.atStartOfDay();
                    LocalDate toDate = LocalDate.parse(d.getString("to"), formatter);
                    LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggal"), 
                            fromDateTime, 
                            toDateTime
                        )
                    );
                }
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
    
    private Key convertKeyDataToKey(KeyData d) {
        Key key = null;
		
        if(d != null) {
            RealmData realmData = d.getRealm();
            Realm realm = realmData != null ?
                    new Realm(
                        realmData.getId(), 
                        realmData.getNama()
                    ) : null;
            
            JwaData jwaData = d.getJwa();            
            
            JwaTypeData jwaTypeData = jwaData != null ? jwaData.getJwa_type() : null;
            
            JwaType jwaType = jwaTypeData != null ?
                    new JwaType(
                        jwaTypeData.getId(), 
                        jwaTypeData.getNama()
                    ) : null;
            
            Jwa jwa = jwaData != null ?
                    new Jwa(
                        jwaData.getId(), 
                        jwaData.getNama(), 
                        jwaData.getKeterangan(), 
                        jwaType
                    ) : null;
            
            EncodingSchemaData encodingSchemaData = d.getEncoding_scheme();
            EncodingScheme encodingScheme = encodingSchemaData != null ?
                    new EncodingScheme(
                        encodingSchemaData.getId(), 
                        encodingSchemaData.getNama()
                    ) : null;
            
            key =  new Key(
                    d.getId(), 
                    realm, 
                    jwa, 
                    encodingScheme, 
                    d.getSecretKey(), 
                    d.getPrivateKey(), 
                    d.getPublicKey(), 
                    d.getTanggal()
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
            
            Realm realm = t.getRealm();
            RealmData realmData = realm != null ? new RealmData(realm.getId()) : null;
            keyData.setRealm(realmData);
            
            Jwa jwa = t.getJwa();                
            JwaData jwaData = jwa != null ? new JwaData(jwa.getId()) : null;
            keyData.setJwa(jwaData);
            
            EncodingScheme encodingScheme = t.getEncoding_scheme();
            EncodingSchemaData encodingSchemaData = encodingScheme != null ? 
                    new EncodingSchemaData(encodingScheme.getId()) : null;
            keyData.setEncoding_scheme(encodingSchemaData);
            
            keyData.setSecretKey(t.getSecred_key());
            keyData.setPrivateKey(t.getPrivate_key());
            keyData.setPublicKey(t.getPublic_key());
            keyData.setTanggal(t.getTanggal());
        }

        return keyData;
    }
    
}
