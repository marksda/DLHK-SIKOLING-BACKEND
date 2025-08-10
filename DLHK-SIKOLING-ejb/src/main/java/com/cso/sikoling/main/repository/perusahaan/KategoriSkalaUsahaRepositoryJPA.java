package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
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


public class KategoriSkalaUsahaRepositoryJPA implements Repository<KategoriSkalaUsaha, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriSkalaUsahaRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriSkalaUsaha save(KategoriSkalaUsaha t) throws SQLException {   
        try {
            KategoriSkalaUsahaData kategoriSkalaUsahaData = convertKategoriSkalaUsahaToKategoriSkalaUsahaData(t);
            entityManager.persist(kategoriSkalaUsahaData);
            entityManager.flush();             
            return convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(kategoriSkalaUsahaData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori skala usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori skala usaha");
        }        
    }

    @Override
    public KategoriSkalaUsaha update(KategoriSkalaUsaha t) throws SQLException {
        
        try {
            KategoriSkalaUsahaData kategoriSkalaUsahaData = convertKategoriSkalaUsahaToKategoriSkalaUsahaData(t);  
            kategoriSkalaUsahaData = entityManager.merge(kategoriSkalaUsahaData);
            return convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(kategoriSkalaUsahaData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori skala usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori skala usaha");
        }
        
    }

    @Override
    public KategoriSkalaUsaha updateId(String idLama, KategoriSkalaUsaha t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriSkalaUsahaData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kategori skala usaha");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori skala usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori skala usaha");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriSkalaUsahaData kategoriSkalaUsahaData = entityManager.find(KategoriSkalaUsahaData.class, id);
            if(kategoriSkalaUsahaData != null) {
                entityManager.remove(kategoriSkalaUsahaData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori skala usaha dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori skala usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriSkalaUsaha> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriSkalaUsahaData> cq = cb.createQuery(KategoriSkalaUsahaData.class);
            Root<KategoriSkalaUsahaData> root = cq.from(KategoriSkalaUsahaData.class);		

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


            TypedQuery<KategoriSkalaUsahaData> typedQuery;	

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
                            .map(d -> convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriSkalaUsahaData.findAll", KategoriSkalaUsahaData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriSkalaUsahaData> root = cq.from(KategoriSkalaUsahaData.class);		

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
    
    private KategoriSkalaUsaha convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(KategoriSkalaUsahaData d) {
        KategoriSkalaUsaha kategoriSkalaUsaha = null;
		
        if(d != null) {
            kategoriSkalaUsaha = new KategoriSkalaUsaha(d.getId(), d.getNama(), d.getSingkatan());
        }

        return kategoriSkalaUsaha;	
    }
    
    private KategoriSkalaUsahaData convertKategoriSkalaUsahaToKategoriSkalaUsahaData(KategoriSkalaUsaha t) {
        KategoriSkalaUsahaData kategoriSkalaUsahaData = null;
		
        if(t != null) {
            kategoriSkalaUsahaData = new KategoriSkalaUsahaData();
            kategoriSkalaUsahaData.setId(t.getId());
            kategoriSkalaUsahaData.setNama(t.getNama());
            kategoriSkalaUsahaData.setSingkatan(t.getSingkatan());
        }

        return kategoriSkalaUsahaData;
    }

}
