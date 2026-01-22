package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
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


public class VersiKbliRepositoryJPA implements Repository<VersiKbli, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public VersiKbliRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public VersiKbli save(VersiKbli t) throws SQLException {   
        try {
            VersiKbliData versiKbliData = convertVersiKbliToVersiKbliData(t);
            entityManager.persist(versiKbliData);
            entityManager.flush();             
            return convertVersiKbliDataToVersiKbli(versiKbliData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id versi kbli harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data versi kbli");
        }        
    }

    @Override
    public VersiKbli update(VersiKbli t) throws SQLException {
        
        try {
            VersiKbliData versiKbliData = convertVersiKbliToVersiKbliData(t);  
            versiKbliData = entityManager.merge(versiKbliData);
            return convertVersiKbliDataToVersiKbli(versiKbliData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id versi kbli harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data versi kbli");
        }
        
    }

    @Override
    public VersiKbli updateId(String idLama, VersiKbli t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("VersiKbliData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id versi kbli");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id versi kbli harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id versi kbli");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            VersiKbliData versiKbliData = entityManager.find(VersiKbliData.class, id);
            if(versiKbliData != null) {
                entityManager.remove(versiKbliData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("versi kbli dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id versi kbli harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<VersiKbli> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<VersiKbliData> cq = cb.createQuery(VersiKbliData.class);
            Root<VersiKbliData> root = cq.from(VersiKbliData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
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
            if(q.getDaftarFieldsSorter() != null) {
                Iterator<SortOrder> iterSort = q.getDaftarFieldsSorter().iterator();
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


            TypedQuery<VersiKbliData> typedQuery;	

            if( q.isIsPaging()) { 
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
                            .map(d -> convertVersiKbliDataToVersiKbli(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("VersiKbliData.findAll", VersiKbliData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertVersiKbliDataToVersiKbli(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<VersiKbliData> root = cq.from(VersiKbliData.class);		

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
    
    private VersiKbli convertVersiKbliDataToVersiKbli(VersiKbliData d) {
        VersiKbli versiKbli = null;
		
        if(d != null) {
            versiKbli = new VersiKbli(d.getId(), d.getNama());
        }

        return versiKbli;	
    }
    
    private VersiKbliData convertVersiKbliToVersiKbliData(VersiKbli t) {
        VersiKbliData versiKbliData = null;
		
        if(t != null) {
            versiKbliData = new VersiKbliData();
            versiKbliData.setId(t.getId());
            versiKbliData.setNama(t.getNama());
        }

        return versiKbliData;
    }

}
