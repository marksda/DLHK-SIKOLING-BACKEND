package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.Paging;
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


public class KabupatenRepositoryJPA implements Repository<Kabupaten, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KabupatenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }    

    @Override
    public Kabupaten save(Kabupaten t) throws SQLException {
        try {
            KabupatenData kabupatenData = convertKabupatenToKabupatenData(t);
            entityManager.persist(kabupatenData);
            entityManager.flush();             
            return convertKabupatenDataToKabupaten(kabupatenData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 4 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kabupaten");
        }    
    }

    @Override
    public Kabupaten update(Kabupaten t) throws SQLException {
        
        try {
            KabupatenData kabupatenData = convertKabupatenToKabupatenData(t);  
            kabupatenData = entityManager.merge(kabupatenData);
            return convertKabupatenDataToKabupaten(kabupatenData);   
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException(cstVltException.getMessage());
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data propinsi");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KabupatenData kabupatenData = entityManager.find(KabupatenData.class, id);
            if(kabupatenData != null) {
                entityManager.remove(kabupatenData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kabupaten dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 4 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public Kabupaten updateId(String idLama, Kabupaten t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KabupatenData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kabupaten");
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id propinsi harus bilangan dan panjang 4 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kabupaten");
        }
    }

    @Override
    public List<Kabupaten> getDaftarData(QueryParamFilters q) {
        List<KabupatenData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KabupatenData> cq = cb.createQuery(KabupatenData.class);
            Root<KabupatenData> root = cq.from(KabupatenData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_propinsi" -> daftarPredicate.add(cb.equal(root.get("propinsi").get("id"), filter.getValue()));
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
                        case "id_propinsi" -> {
                            if(sort.getValue().equals("ASC")) {
                                cq.orderBy(cb.asc(root.get("propinsi").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("propinsi").get("id")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KabupatenData> typedQuery;	

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
                            .map(d -> convertKabupatenDataToKabupaten(d))
                            .collect(Collectors.toList());
            } 
        }
        else {
            hasil = entityManager.createNamedQuery(
                                        "KabupatenData.findAll", 
                                        KabupatenData.class
                                    ).getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                        .map(d -> convertKabupatenDataToKabupaten(d))
                        .collect(Collectors.toList());
            }
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KabupatenData> root = cq.from(KabupatenData.class);		

        // where clause
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        if( f != null) {
            Iterator<Filter> iterFilter = f.iterator();

            while (iterFilter.hasNext()) {
                Filter filter = (Filter) iterFilter.next();

                switch (filter.getField_name()) {
                    case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                    case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    case "id_propinsi" -> daftarPredicate.add(cb.equal(root.get("propinsi").get("id"), filter.getValue()));
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
    
    private Kabupaten convertKabupatenDataToKabupaten(KabupatenData d) {
        Kabupaten kabupaten = null;
		
        if(d != null) {
            kabupaten = new Kabupaten(d.getId(), d.getNama(), d.getPropinsi().getId());
        }

        return kabupaten;	
    }
    
    private KabupatenData convertKabupatenToKabupatenData(Kabupaten t) {
        KabupatenData kabupatenData = null;
		
        if(t != null) {
            PropinsiData propinsiData = new PropinsiData();
            propinsiData.setId(t.getId_propinsi());
            
            kabupatenData = new KabupatenData();
            kabupatenData.setId(t.getId());
            kabupatenData.setNama(t.getNama());
            kabupatenData.setPropinsi(propinsiData);
        }

        return kabupatenData;
    }

}
