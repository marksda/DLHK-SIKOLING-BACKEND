package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.dokumen.StatusDokumen;
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


public class StatusDokumenRepositoryJPA implements Repository<StatusDokumen, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public StatusDokumenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public StatusDokumen save(StatusDokumen t) throws SQLException {   
        try {
            StatusDokumenData statusDokumenData = convertStatusDokumenToStatusDokumenData(t);
            entityManager.persist(statusDokumenData);
            entityManager.flush();             
            return convertStatusDokumenDataToStatusDokumen(statusDokumenData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status dokumen harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status dokumen");
        }        
    }

    @Override
    public StatusDokumen update(StatusDokumen t) throws SQLException {
        
        try {
            StatusDokumenData statusDokumenData = convertStatusDokumenToStatusDokumenData(t);  
            statusDokumenData = entityManager.merge(statusDokumenData);
            return convertStatusDokumenDataToStatusDokumen(statusDokumenData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status dokumen harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status dokumen");
        }
        
    }

    @Override
    public StatusDokumen updateId(String idLama, StatusDokumen t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("StatusDokumenData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id status dokumen");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status dokumen harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id status dokumen");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            StatusDokumenData statusDokumenData = entityManager.find(StatusDokumenData.class, id);
            if(statusDokumenData != null) {
                entityManager.remove(statusDokumenData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("status dokumen dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status dokumen harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<StatusDokumen> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<StatusDokumenData> cq = cb.createQuery(StatusDokumenData.class);
            Root<StatusDokumenData> root = cq.from(StatusDokumenData.class);		

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


            TypedQuery<StatusDokumenData> typedQuery;	

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
                            .map(d -> convertStatusDokumenDataToStatusDokumen(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("StatusDokumenData.findAll", StatusDokumenData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertStatusDokumenDataToStatusDokumen(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<StatusDokumenData> root = cq.from(StatusDokumenData.class);		

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
    
    private StatusDokumen convertStatusDokumenDataToStatusDokumen(StatusDokumenData d) {
        StatusDokumen statusDokumen = null;
		
        if(d != null) {
            statusDokumen = new StatusDokumen(d.getId(), d.getNama());
        }

        return statusDokumen;	
    }
    
    private StatusDokumenData convertStatusDokumenToStatusDokumenData(StatusDokumen t) {
        StatusDokumenData statusDokumenData = null;
		
        if(t != null) {
            statusDokumenData = new StatusDokumenData();
            String idStatusDokumen = t.getId();
            statusDokumenData.setId(idStatusDokumen != null ? t.getId() : getGenerateId());
            statusDokumenData.setNama(t.getNama());
        }

        return statusDokumenData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM StatusDokumenData m");

        try {
                hasil = (String) q.getSingleResult();
                Long idBaru = Long.parseLong(hasil)  + 1;
                hasil = Long.toString(idBaru);
                return hasil;
        } catch (NumberFormatException e) {	
                hasil = "0";			
                return hasil;
        }		
    }

}
