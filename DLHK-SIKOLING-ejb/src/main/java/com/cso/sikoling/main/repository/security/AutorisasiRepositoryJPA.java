package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.repository.person.PersonData;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class AutorisasiRepositoryJPA implements Repository<Autorisasi, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public AutorisasiRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Autorisasi save(Autorisasi t) throws SQLException {   
        try {
            AutorisasiData autorisasiData = convertAutorisasiToAutorisasiData(t);
            entityManager.persist(autorisasiData);
            entityManager.flush();             
            return convertAutorisasiDataToAutorisasi(autorisasiData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data autorisasi");
        }        
    }

    @Override
    public Autorisasi update(Autorisasi t) throws SQLException {
        
        try {
            AutorisasiData autorisasiData = convertAutorisasiToAutorisasiData(t);  
            autorisasiData = entityManager.merge(autorisasiData);
            return convertAutorisasiDataToAutorisasi(autorisasiData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data autorisasi");
        }
        
    }

    @Override
    public Autorisasi updateId(String idLama, Autorisasi t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("AutorisasiData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id autorisasi");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id autorisasi");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            AutorisasiData autorisasiData = entityManager.find(AutorisasiData.class, id);
            if(autorisasiData != null) {
                entityManager.remove(autorisasiData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("autorisasi dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Autorisasi> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AutorisasiData> cq = cb.createQuery(AutorisasiData.class);
            Root<AutorisasiData> root = cq.from(AutorisasiData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
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
                        case "user_name" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("userName")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("userName")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<AutorisasiData> typedQuery;	

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
                            .map(d -> convertAutorisasiDataToAutorisasi(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("AutorisasiData.findAll", AutorisasiData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertAutorisasiDataToAutorisasi(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<AutorisasiData> root = cq.from(AutorisasiData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
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
    
    private Autorisasi convertAutorisasiDataToAutorisasi(AutorisasiData d) {
        Autorisasi autorisasi = null;
		
        if(d != null) {
            autorisasi = new Autorisasi(
                    d.getId(), 
                    d.getIdLama(), 
                    d.getStatusInternal(), 
                    d.getIsVerified(), 
                    d.getUserName(), 
                    d.getTanggalRegistrasi(), 
                    d.getHakAkses().getId(), 
                    d.getPerson().getId()
                );
        }

        return autorisasi;	
    }
    
    private AutorisasiData convertAutorisasiToAutorisasiData(Autorisasi t) {
        AutorisasiData autorisasiData = null;
		
        if(t != null) {
            autorisasiData = new AutorisasiData();
            autorisasiData.setId(t.getId());
            autorisasiData.setIdLama(t.getId_lama());
            autorisasiData.setStatusInternal(t.getStatus_internal());
            autorisasiData.setIsVerified(t.getIs_verified());
            autorisasiData.setUserName(t.getUser_name());
            autorisasiData.setTanggalRegistrasi(t.getTanggal_registrasi());
            autorisasiData.setHakAkses(new HakAksesData(t.getId_hak_akses()));
            autorisasiData.setPerson(new PersonData(t.getId_person()));
        }

        return autorisasiData;
    }

}
