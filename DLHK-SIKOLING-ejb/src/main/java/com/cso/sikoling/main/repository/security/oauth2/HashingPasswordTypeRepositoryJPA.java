package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.HashingPasswordType;
import com.cso.sikoling.abstraction.repository.Repository;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class HashingPasswordTypeRepositoryJPA implements Repository<HashingPasswordType, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public HashingPasswordTypeRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public HashingPasswordType save(HashingPasswordType t) throws SQLException {   
        try {
            HashingPasswordTypeData hashingPasswordTypeData = convertHashingPasswordTypeToHashingPasswordTypeData(t);
            entityManager.persist(hashingPasswordTypeData);
            entityManager.flush();             
            return convertHashingPasswordTypeDataToHashingPasswordType(hashingPasswordTypeData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hashing password type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data hashing password type");
        }        
    }

    @Override
    public HashingPasswordType update(HashingPasswordType t) throws SQLException {
        
        try {
            HashingPasswordTypeData hashingPasswordTypeData = convertHashingPasswordTypeToHashingPasswordTypeData(t);  
            hashingPasswordTypeData = entityManager.merge(hashingPasswordTypeData);
            return convertHashingPasswordTypeDataToHashingPasswordType(hashingPasswordTypeData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hashing password type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data hashing password type");
        }
        
    }

    @Override
    public HashingPasswordType updateId(String idLama, HashingPasswordType t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("HashingPasswordTypeData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id hashing password type");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hashing password type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id hashing password type");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            HashingPasswordTypeData hashingPasswordTypeData = entityManager.find(HashingPasswordTypeData.class, id);
            if(hashingPasswordTypeData != null) {
                entityManager.remove(hashingPasswordTypeData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("hashing password type dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hashing password type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<HashingPasswordType> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<HashingPasswordTypeData> cq = cb.createQuery(HashingPasswordTypeData.class);
            Root<HashingPasswordTypeData> root = cq.from(HashingPasswordTypeData.class);		

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


            TypedQuery<HashingPasswordTypeData> typedQuery;	

            if( q.isIs_paging()) { 
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
                            .map(d -> convertHashingPasswordTypeDataToHashingPasswordType(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("HashingPasswordTypeData.findAll", HashingPasswordTypeData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertHashingPasswordTypeDataToHashingPasswordType(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<HashingPasswordTypeData> root = cq.from(HashingPasswordTypeData.class);		

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
    
    private HashingPasswordType convertHashingPasswordTypeDataToHashingPasswordType(HashingPasswordTypeData d) {
        HashingPasswordType jwaType = null;
		
        if(d != null) {
            jwaType = new HashingPasswordType(d.getId(), d.getNama());
        }

        return jwaType;	
    }
    
    private HashingPasswordTypeData convertHashingPasswordTypeToHashingPasswordTypeData(HashingPasswordType t) {
        HashingPasswordTypeData hashingPasswordTypeData = null;
		
        if(t != null) {
            hashingPasswordTypeData = new HashingPasswordTypeData();
            hashingPasswordTypeData.setId(t.getId());
            hashingPasswordTypeData.setNama(t.getNama());
        }

        return hashingPasswordTypeData;
    }

}
