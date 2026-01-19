package com.cso.sikoling.main.repository.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
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


public class JenisKelaminRepositoryJPA implements Repository<JenisKelamin, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public JenisKelaminRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public JenisKelamin save(JenisKelamin t) throws SQLException {   
        try {
            JenisKelaminData jenisKelaminData = convertJenisKelaminToJenisKelaminData(t);
            entityManager.persist(jenisKelaminData);
            entityManager.flush();             
            return convertJenisKelaminDataToJenisKelamin(jenisKelaminData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jenisKelamin harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jenisKelamin");
        }        
    }

    @Override
    public JenisKelamin update(JenisKelamin t) throws SQLException {
        
        try {
            JenisKelaminData jenisKelaminData = convertJenisKelaminToJenisKelaminData(t);  
            jenisKelaminData = entityManager.merge(jenisKelaminData);
            return convertJenisKelaminDataToJenisKelamin(jenisKelaminData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jenisKelamin harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jenisKelamin");
        }
        
    }

    @Override
    public JenisKelamin updateId(String idLama, JenisKelamin t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("JenisKelaminData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id jenisKelamin");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jenisKelamin harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id jenisKelamin");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            JenisKelaminData jenisKelaminData = entityManager.find(JenisKelaminData.class, id);
            if(jenisKelaminData != null) {
                entityManager.remove(jenisKelaminData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("jenisKelamin dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jenisKelamin harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<JenisKelamin> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<JenisKelaminData> cq = cb.createQuery(JenisKelaminData.class);
            Root<JenisKelaminData> root = cq.from(JenisKelaminData.class);		

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


            TypedQuery<JenisKelaminData> typedQuery;	

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
                            .map(d -> convertJenisKelaminDataToJenisKelamin(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("JenisKelaminData.findAll", JenisKelaminData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertJenisKelaminDataToJenisKelamin(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<JenisKelaminData> root = cq.from(JenisKelaminData.class);		

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
    
    private JenisKelamin convertJenisKelaminDataToJenisKelamin(JenisKelaminData d) {
        JenisKelamin jenisKelamin = null;
		
        if(d != null) {
            jenisKelamin = new JenisKelamin(d.getId(), d.getNama());
        }

        return jenisKelamin;	
    }
    
    private JenisKelaminData convertJenisKelaminToJenisKelaminData(JenisKelamin t) {
        JenisKelaminData jenisKelaminData = null;
		
        if(t != null) {
            jenisKelaminData = new JenisKelaminData();
            jenisKelaminData.setId(t.getId());
            jenisKelaminData.setNama(t.getNama());
        }

        return jenisKelaminData;
    }

}
