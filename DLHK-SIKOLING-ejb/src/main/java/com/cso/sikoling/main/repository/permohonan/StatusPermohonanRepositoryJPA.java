package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.permohonan.StatusPermohonan;
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


public class StatusPermohonanRepositoryJPA implements Repository<StatusPermohonan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public StatusPermohonanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public StatusPermohonan save(StatusPermohonan t) throws SQLException {   
        try {
            StatusPermohonanData statusPermohonanData = convertStatusPermohonanToStatusPermohonanData(t);
            entityManager.persist(statusPermohonanData);
            entityManager.flush();             
            return convertStatusPermohonanDataToStatusPermohonan(statusPermohonanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status permohonan harus bilangan 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status permohonan");
        }        
    }

    @Override
    public StatusPermohonan update(StatusPermohonan t) throws SQLException {
        
        try {
            StatusPermohonanData statusPermohonanData = convertStatusPermohonanToStatusPermohonanData(t);  
            statusPermohonanData = entityManager.merge(statusPermohonanData);
            return convertStatusPermohonanDataToStatusPermohonan(statusPermohonanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status permohonan harus bilangan 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data status permohonan");
        }
        
    }

    @Override
    public StatusPermohonan updateId(String idLama, StatusPermohonan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("StatusPermohonanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id status permohonan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status permohonan harus bilangan 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id status permohonan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            StatusPermohonanData statusPermohonanData = entityManager.find(StatusPermohonanData.class, id);
            if(statusPermohonanData != null) {
                entityManager.remove(statusPermohonanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("status permohonan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id status permohonan harus bilangan 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<StatusPermohonan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<StatusPermohonanData> cq = cb.createQuery(StatusPermohonanData.class);
            Root<StatusPermohonanData> root = cq.from(StatusPermohonanData.class);		

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


            TypedQuery<StatusPermohonanData> typedQuery;	

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
                            .map(d -> convertStatusPermohonanDataToStatusPermohonan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("StatusPermohonanData.findAll", StatusPermohonanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertStatusPermohonanDataToStatusPermohonan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<StatusPermohonanData> root = cq.from(StatusPermohonanData.class);		

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
    
    private StatusPermohonan convertStatusPermohonanDataToStatusPermohonan(StatusPermohonanData d) {
        StatusPermohonan statusPermohonan = null;
		
        if(d != null) {
            statusPermohonan = new StatusPermohonan(d.getId(), d.getNama());
        }

        return statusPermohonan;	
    }
    
    private StatusPermohonanData convertStatusPermohonanToStatusPermohonanData(StatusPermohonan t) {
        StatusPermohonanData statusFlowPermohonanData = null;
		
        if(t != null) {
            statusFlowPermohonanData = new StatusPermohonanData();
            String id = t.getId();
            statusFlowPermohonanData.setId(id != null ? id : getGenerateId());
            statusFlowPermohonanData.setNama(t.getNama());
        }

        return statusFlowPermohonanData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM StatusPermohonanData m");

        try {
                hasil = (String) q.getSingleResult();
                Long idBaru = Long.parseLong(hasil)  + 1;
                hasil = LPad(Long.toString(idBaru), 2, '0');                
                return hasil;
        } catch (NumberFormatException e) {	
                hasil = "0";			
                return hasil;
        }		
    }
    
    private String LPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

}
