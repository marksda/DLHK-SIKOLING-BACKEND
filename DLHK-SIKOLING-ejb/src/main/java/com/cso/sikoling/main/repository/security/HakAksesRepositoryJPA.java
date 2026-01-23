package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
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


public class HakAksesRepositoryJPA implements Repository<HakAkses, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public HakAksesRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public HakAkses save(HakAkses t) throws SQLException {   
        try {
            HakAksesData hakAksesData = convertHakAksesToHakAksesData(t);
            entityManager.persist(hakAksesData);
            entityManager.flush();             
            return convertHakAksesDataToHakAkses(hakAksesData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hak akses harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data hak akses");
        }        
    }

    @Override
    public HakAkses update(HakAkses t) throws SQLException {
        
        try {
            HakAksesData hakAksesData = convertHakAksesToHakAksesData(t);  
            hakAksesData = entityManager.merge(hakAksesData);
            return convertHakAksesDataToHakAkses(hakAksesData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hak akses harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data hak akses");
        }
        
    }

    @Override
    public HakAkses updateId(String idLama, HakAkses t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("HakAksesData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id hak akses");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hak akses harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id hak akses");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            HakAksesData hakAksesData = entityManager.find(HakAksesData.class, id);
            if(hakAksesData != null) {
                entityManager.remove(hakAksesData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("hak akses dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id hak akses harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<HakAkses> getDaftarData(QueryParamFilters q) {
        
        List<HakAksesData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<HakAksesData> cq = cb.createQuery(HakAksesData.class);
            Root<HakAksesData> root = cq.from(HakAksesData.class);		

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


            TypedQuery<HakAksesData> typedQuery;	

            if( q.isIsPaging()) { 
                Paging paging = q.getPaging();
                typedQuery = entityManager.createQuery(cq)
                                .setMaxResults(paging.getPage_size())
                                .setFirstResult((paging.getPage_number()-1)*paging.getPage_size());
            }
            else {
                typedQuery = entityManager.createQuery(cq);
            }
            
            hasil = typedQuery.getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                            .map(d -> convertHakAksesDataToHakAkses(d))
                            .collect(Collectors.toList());
            }
        }
        else {
            hasil = entityManager.createNamedQuery(
                    "HakAksesData.findAll", HakAksesData.class).getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                            .map(d -> convertHakAksesDataToHakAkses(d))
                            .collect(Collectors.toList());
            }
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<HakAksesData> root = cq.from(HakAksesData.class);	
        
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        if( f != null) {
            Iterator<Filter> iterFilter = f.iterator();
            while (iterFilter.hasNext()) {
                Filter filter = (Filter) iterFilter.next();

                switch (filter.getField_name()) {
                    case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                    case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    default -> {
                    }
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
    
    private HakAkses convertHakAksesDataToHakAkses(HakAksesData d) {
        HakAkses hakAkses = null;
		
        if(d != null) {
            hakAkses = new HakAkses(d.getId(), d.getNama(), d.getKeterangan());
        }

        return hakAkses;	
    }
    
    private HakAksesData convertHakAksesToHakAksesData(HakAkses t) {
        HakAksesData hakAksesData = null;
		
        if(t != null) {
            hakAksesData = new HakAksesData();
            hakAksesData.setId(t.getId());
            hakAksesData.setNama(t.getNama());
            hakAksesData.setKeterangan(t.getKeterangan());
        }

        return hakAksesData;
    }

}
