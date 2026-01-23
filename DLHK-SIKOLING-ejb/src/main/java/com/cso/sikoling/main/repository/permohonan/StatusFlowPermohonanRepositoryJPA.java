package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.permohonan.StatusFlowPermohonan;
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


public class StatusFlowPermohonanRepositoryJPA implements Repository<StatusFlowPermohonan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public StatusFlowPermohonanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public StatusFlowPermohonan save(StatusFlowPermohonan t) throws SQLException {   
        try {
            StatusFlowPermohonanData statusFlowPermohonanData = convertStatusFlowPermohonanToStatusFlowPermohonanData(t);
            entityManager.persist(statusFlowPermohonanData);
            entityManager.flush();             
            return convertStatusFlowPermohonanDataToStatusFlowPermohonan(statusFlowPermohonanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status flow permohonan harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status flow permohonan");
        }        
    }

    @Override
    public StatusFlowPermohonan update(StatusFlowPermohonan t) throws SQLException {
        
        try {
            StatusFlowPermohonanData statusFlowPermohonanData = convertStatusFlowPermohonanToStatusFlowPermohonanData(t);  
            statusFlowPermohonanData = entityManager.merge(statusFlowPermohonanData);
            return convertStatusFlowPermohonanDataToStatusFlowPermohonan(statusFlowPermohonanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status flow permohonan harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status flow permohonan");
        }
        
    }

    @Override
    public StatusFlowPermohonan updateId(String idLama, StatusFlowPermohonan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("StatusFlowPermohonanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id status flow permohonan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status flow permohonan harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id status flow permohonan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            StatusFlowPermohonanData statusFlowPermohonanData = entityManager.find(StatusFlowPermohonanData.class, id);
            if(statusFlowPermohonanData != null) {
                entityManager.remove(statusFlowPermohonanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("status flow permohonan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status flow permohonan harus bilangan 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<StatusFlowPermohonan> getDaftarData(QueryParamFilters q) {
        
        List<StatusFlowPermohonanData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<StatusFlowPermohonanData> cq = cb.createQuery(StatusFlowPermohonanData.class);
            Root<StatusFlowPermohonanData> root = cq.from(StatusFlowPermohonanData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "keterangan" -> daftarPredicate.add(cb.like(cb.lower(root.get("keterangan")), "%"+filter.getValue().toLowerCase()+"%"));
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
                        case "keterangan" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("keterangan")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("keterangan")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<StatusFlowPermohonanData> typedQuery;	

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
                            .map(d -> convertStatusFlowPermohonanDataToStatusFlowPermohonan(d))
                            .collect(Collectors.toList());
            }
        }
        else {
            hasil = entityManager.createNamedQuery(
                    "StatusFlowPermohonanData.findAll", 
                    StatusFlowPermohonanData.class).getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                            .map(d -> convertStatusFlowPermohonanDataToStatusFlowPermohonan(d))
                            .collect(Collectors.toList());
            }
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<StatusFlowPermohonanData> root = cq.from(StatusFlowPermohonanData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "keterangan" -> daftarPredicate.add(cb.like(cb.lower(root.get("keterangan")), "%"+filter.getValue().toLowerCase()+"%"));
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
    
    private StatusFlowPermohonan convertStatusFlowPermohonanDataToStatusFlowPermohonan(StatusFlowPermohonanData d) {
        StatusFlowPermohonan statusFlowPermohonan = null;
		
        if(d != null) {
            statusFlowPermohonan = new StatusFlowPermohonan(d.getId(), d.getKeterangan());
        }

        return statusFlowPermohonan;	
    }
    
    private StatusFlowPermohonanData convertStatusFlowPermohonanToStatusFlowPermohonanData(StatusFlowPermohonan t) {
        StatusFlowPermohonanData statusFlowPermohonanData = null;
		
        if(t != null) {
            statusFlowPermohonanData = new StatusFlowPermohonanData();
            String id = t.getId();
            statusFlowPermohonanData.setId(id != null ? id : getGenerateId());
            statusFlowPermohonanData.setKeterangan(t.getKeterangan());
        }

        return statusFlowPermohonanData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM StatusFlowPermohonanData m");

        try {
                hasil = (String) q.getSingleResult();
                Long idBaru = Long.parseLong(hasil)  + 1;
//                hasil = LPad(Long.toString(idBaru), 2, '0');                
                return Long.toString(idBaru);
        } catch (NumberFormatException e) {	
                hasil = "0";			
                return hasil;
        }		
    }

}
