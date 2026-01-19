package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
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


public class JwaTypeRepositoryJPA implements Repository<JwaType, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public JwaTypeRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public JwaType save(JwaType t) throws SQLException {   
        try {
            JwaTypeData jwaTypeData = convertJwaTypeToJwaTypeData(t);
            entityManager.persist(jwaTypeData);
            entityManager.flush();             
            return convertJwaTypeDataToJwaType(jwaTypeData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jwa type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jwa type");
        }        
    }

    @Override
    public JwaType update(JwaType t) throws SQLException {
        
        try {
            JwaTypeData jwaTypeData = convertJwaTypeToJwaTypeData(t);  
            jwaTypeData = entityManager.merge(jwaTypeData);
            return convertJwaTypeDataToJwaType(jwaTypeData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jwa type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jwa type");
        }
        
    }

    @Override
    public JwaType updateId(String idLama, JwaType t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("JwaTypeData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id jwa type");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jwa type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id jwa type");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            JwaTypeData jwaTypeData = entityManager.find(JwaTypeData.class, id);
            if(jwaTypeData != null) {
                entityManager.remove(jwaTypeData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("jwa type dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jwa type harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<JwaType> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<JwaTypeData> cq = cb.createQuery(JwaTypeData.class);
            Root<JwaTypeData> root = cq.from(JwaTypeData.class);		

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


            TypedQuery<JwaTypeData> typedQuery;	

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
                            .map(d -> convertJwaTypeDataToJwaType(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("JwaTypeData.findAll", JwaTypeData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertJwaTypeDataToJwaType(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<JwaTypeData> root = cq.from(JwaTypeData.class);		

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
    
    private JwaType convertJwaTypeDataToJwaType(JwaTypeData d) {
        JwaType jwaType = null;
		
        if(d != null) {
            jwaType = new JwaType(d.getId(), d.getNama());
        }

        return jwaType;	
    }
    
    private JwaTypeData convertJwaTypeToJwaTypeData(JwaType t) {
        JwaTypeData jwaTypeData = null;
		
        if(t != null) {
            jwaTypeData = new JwaTypeData();
            jwaTypeData.setId(t.getId());
            jwaTypeData.setNama(t.getNama());
        }

        return jwaTypeData;
    }

}
