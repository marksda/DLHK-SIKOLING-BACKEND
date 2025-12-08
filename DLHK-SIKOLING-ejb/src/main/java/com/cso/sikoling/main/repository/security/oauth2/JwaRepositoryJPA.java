package com.cso.sikoling.main.repository.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
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


public class JwaRepositoryJPA implements Repository<Jwa, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public JwaRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Jwa save(Jwa t) throws SQLException {   
        try {
            JwaData jwaData = convertJwaToJwaData(t);
            entityManager.persist(jwaData);
            entityManager.flush();             
            return convertJwaDataToJwa(jwaData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data realm");
        }        
    }

    @Override
    public Jwa update(Jwa t) throws SQLException {
        
        try {
            JwaData jwaData = convertJwaToJwaData(t);  
            jwaData = entityManager.merge(jwaData);
            return convertJwaDataToJwa(jwaData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data realm");
        }
        
    }

    @Override
    public Jwa updateId(String idLama, Jwa t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("JwaData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id realm");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id realm");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            JwaData jwaData = entityManager.find(JwaData.class, id);
            if(jwaData != null) {
                entityManager.remove(jwaData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("realm dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id realm harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Jwa> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<JwaData> cq = cb.createQuery(JwaData.class);
            Root<JwaData> root = cq.from(JwaData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_jwa_type" -> daftarPredicate.add(cb.equal(root.get("jwa_type").get("id"), filter.getValue()));
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
                        case "jwa_type" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("jwa_type").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("jwa_type").get("nama")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<JwaData> typedQuery;	

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
                            .map(d -> convertJwaDataToJwa(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("JwaData.findAll", JwaData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertJwaDataToJwa(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<JwaData> root = cq.from(JwaData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                case "id_jwa_type" -> daftarPredicate.add(cb.equal(root.get("jwa_type").get("id"), filter.getValue()));
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
    
    private Jwa convertJwaDataToJwa(JwaData d) {
        Jwa jwa = null;
		
        if(d != null) {
            JwaTypeData jwaTypeData = d.getJwa_type();
            JwaType jwaType = jwaTypeData != null ?
                    new JwaType(
                        jwaTypeData.getId(),
                        jwaTypeData.getNama()
                    ) 
                    : null;
            jwa = new Jwa(
                    d.getId(), 
                    d.getNama(), 
                    d.getKeterangan(), 
                    jwaType
                );
        }

        return jwa;	
    }
    
    private JwaData convertJwaToJwaData(Jwa t) {
        JwaData jwaData = null;
		
        if(t != null) {
            jwaData = new JwaData();
            jwaData.setId(t.getId());
            jwaData.setNama(t.getNama());
            jwaData.setKeterangan(t.getKeterangan());
            
            JwaTypeData jwaTypeData = new JwaTypeData();
            JwaType jwaType = t.getJwa_type();
            jwaTypeData.setId(jwaType.getId());
            jwaTypeData.setNama(jwaType.getNama());
            jwaData.setJwa_type(jwaTypeData);            
        }

        return jwaData;
    }

}
