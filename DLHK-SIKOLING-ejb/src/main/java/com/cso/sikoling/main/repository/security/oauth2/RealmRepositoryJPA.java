package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
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


public class RealmRepositoryJPA implements Repository<Realm, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public RealmRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Realm save(Realm t) throws SQLException {   
        try {
            RealmData realmData = convertRealmToRealmData(t);
            entityManager.persist(realmData);
            entityManager.flush();             
            return convertRealmDataToRealm(realmData);  
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data realm");
        }        
    }

    @Override
    public Realm update(Realm t) throws SQLException {
        
        try {
            RealmData realmData = convertRealmToRealmData(t);  
            realmData = entityManager.merge(realmData);
            return convertRealmDataToRealm(realmData);   
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus 38 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data realm");
        }
        
    }

    @Override
    public Realm updateId(String idLama, Realm t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("RealmData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return t;
            }
            else {
                throw new SQLException("Gagal mengupdate id realm");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id realm");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            RealmData realmData = entityManager.find(RealmData.class, id);
            if(realmData != null) {
                entityManager.remove(realmData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("realm dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus 36 karakter");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Realm> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<RealmData> cq = cb.createQuery(RealmData.class);
            Root<RealmData> root = cq.from(RealmData.class);		

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


            TypedQuery<RealmData> typedQuery;	

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
                            .map(d -> convertRealmDataToRealm(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("RealmData.findAll", RealmData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertRealmDataToRealm(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<RealmData> root = cq.from(RealmData.class);		

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
    
    private Realm convertRealmDataToRealm(RealmData d) {
        Realm jwa = null;
		
        if(d != null) {
            jwa = new Realm(d.getId(), d.getNama());
        }

        return jwa;	
    }
    
    private RealmData convertRealmToRealmData(Realm t) {
        RealmData realmData = null;
		
        if(t != null) {
            realmData = new RealmData();
            realmData.setId(t.getId());
            realmData.setNama(t.getNama());
        }

        return realmData;
    }

}
