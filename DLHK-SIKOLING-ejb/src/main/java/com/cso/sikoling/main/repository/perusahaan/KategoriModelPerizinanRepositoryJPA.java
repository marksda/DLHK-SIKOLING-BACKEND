package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
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


public class KategoriModelPerizinanRepositoryJPA implements Repository<KategoriModelPerizinan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriModelPerizinanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriModelPerizinan save(KategoriModelPerizinan t) throws SQLException {   
        try {
            KategoriModelPerizinanData kategoriModelPerizinanData = convertKategoriModelPerizinanToKategoriModelPerizinanData(t);
            entityManager.persist(kategoriModelPerizinanData);
            entityManager.flush();             
            return convertKategoriModelPerizinanDataToKategoriModelPerizinan(kategoriModelPerizinanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori model perizinan harus bilangan dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori model perizinan");
        }        
    }

    @Override
    public KategoriModelPerizinan update(KategoriModelPerizinan t) throws SQLException {
        
        try {
            KategoriModelPerizinanData kategoriModelPerizinanData = convertKategoriModelPerizinanToKategoriModelPerizinanData(t);  
            kategoriModelPerizinanData = entityManager.merge(kategoriModelPerizinanData);
            return convertKategoriModelPerizinanDataToKategoriModelPerizinan(kategoriModelPerizinanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori model perizinan harus bilangan dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori model perizinan");
        }
        
    }

    @Override
    public KategoriModelPerizinan updateId(String idLama, KategoriModelPerizinan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriModelPerizinanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kategori model perizinan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori model perizinan harus bilangan dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori model perizinan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriModelPerizinanData kategoriModelPerizinanData = entityManager.find(KategoriModelPerizinanData.class, id);
            if(kategoriModelPerizinanData != null) {
                entityManager.remove(kategoriModelPerizinanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori model perizinan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori model perizinan harus bilangan dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriModelPerizinan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriModelPerizinanData> cq = cb.createQuery(KategoriModelPerizinanData.class);
            Root<KategoriModelPerizinanData> root = cq.from(KategoriModelPerizinanData.class);		

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


            TypedQuery<KategoriModelPerizinanData> typedQuery;	

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
                            .map(d -> convertKategoriModelPerizinanDataToKategoriModelPerizinan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriModelPerizinanData.findAll", KategoriModelPerizinanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriModelPerizinanDataToKategoriModelPerizinan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriModelPerizinanData> root = cq.from(KategoriModelPerizinanData.class);		

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
    
    private KategoriModelPerizinan convertKategoriModelPerizinanDataToKategoriModelPerizinan(KategoriModelPerizinanData d) {
        KategoriModelPerizinan kategoriModelPerizinan = null;
		
        if(d != null) {
            kategoriModelPerizinan = new KategoriModelPerizinan(d.getId(), d.getNama(), d.getSingkatan());
        }

        return kategoriModelPerizinan;	
    }
    
    private KategoriModelPerizinanData convertKategoriModelPerizinanToKategoriModelPerizinanData(KategoriModelPerizinan t) {
        KategoriModelPerizinanData kategoriModelPerizinanData = null;
		
        if(t != null) {
            kategoriModelPerizinanData = new KategoriModelPerizinanData();
            kategoriModelPerizinanData.setId(t.getId());
            kategoriModelPerizinanData.setNama(t.getNama());
            kategoriModelPerizinanData.setSingkatan(t.getSingkatan());
        }

        return kategoriModelPerizinanData;
    }

}
