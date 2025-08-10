package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
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


public class PropinsiRepositoryJPA implements Repository<Propinsi, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PropinsiRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Propinsi save(Propinsi t) throws SQLException {   
        try {
            PropinsiData propinsiData = convertPropinsiToPropinsiData(t);
            entityManager.persist(propinsiData);
            entityManager.flush();             
            return convertPropinsiDataToPropinsi(propinsiData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data propinsi");
        }        
    }

    @Override
    public Propinsi update(Propinsi t) throws SQLException {
        
        try {
            PropinsiData propinsiData = convertPropinsiToPropinsiData(t);  
            propinsiData = entityManager.merge(propinsiData);
            return convertPropinsiDataToPropinsi(propinsiData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data propinsi");
        }
        
    }

    @Override
    public Propinsi updateId(String idLama, Propinsi t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PropinsiData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id propinsi");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id propinsi");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            PropinsiData propinsiData = entityManager.find(PropinsiData.class, id);
            if(propinsiData != null) {
                entityManager.remove(propinsiData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("propinsi dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Propinsi> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PropinsiData> cq = cb.createQuery(PropinsiData.class);
            Root<PropinsiData> root = cq.from(PropinsiData.class);		

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


            TypedQuery<PropinsiData> typedQuery;	

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
                            .map(d -> convertPropinsiDataToPropinsi(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("PropinsiData.findAll", PropinsiData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertPropinsiDataToPropinsi(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PropinsiData> root = cq.from(PropinsiData.class);		

        // where clause
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
    
    private Propinsi convertPropinsiDataToPropinsi(PropinsiData d) {
        Propinsi propinsi = null;
		
        if(d != null) {
            propinsi = new Propinsi(d.getId(), d.getNama());
        }

        return propinsi;	
    }
    
    private PropinsiData convertPropinsiToPropinsiData(Propinsi t) {
        PropinsiData propinsiData = null;
		
        if(t != null) {
            propinsiData = new PropinsiData();
            propinsiData.setId(t.getId());
            propinsiData.setNama(t.getNama());
        }

        return propinsiData;
    }

}
