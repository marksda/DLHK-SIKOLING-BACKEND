package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
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


public class KategoriPelakuUsahaRepositoryJPA implements Repository<KategoriPelakuUsaha, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriPelakuUsahaRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriPelakuUsaha save(KategoriPelakuUsaha t) throws SQLException {   
        try {
            KategoriPelakuUsahaData kategoriPelakuUsahaData = convertKategoriPelakuUsahaToKategoriPelakuUsahaData(t);
            entityManager.persist(kategoriPelakuUsahaData);
            entityManager.flush();             
            return convertKategoriPelakuUsahaDataToKategoriPelakuUsaha(kategoriPelakuUsahaData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pelaku usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori pelaku usaha");
        }        
    }

    @Override
    public KategoriPelakuUsaha update(KategoriPelakuUsaha t) throws SQLException {
        
        try {
            KategoriPelakuUsahaData kategoriPelakuUsahaData = convertKategoriPelakuUsahaToKategoriPelakuUsahaData(t);  
            kategoriPelakuUsahaData = entityManager.merge(kategoriPelakuUsahaData);
            return convertKategoriPelakuUsahaDataToKategoriPelakuUsaha(kategoriPelakuUsahaData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pelaku usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori pelaku usaha");
        }
        
    }

    @Override
    public KategoriPelakuUsaha updateId(String idLama, KategoriPelakuUsaha t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriPelakuUsahaData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kategori pelaku usaha");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pelaku usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori pelaku usaha");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriPelakuUsahaData kategoriPelakuUsahaData = entityManager.find(KategoriPelakuUsahaData.class, id);
            if(kategoriPelakuUsahaData != null) {
                entityManager.remove(kategoriPelakuUsahaData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori pelaku usaha dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pelaku usaha harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriPelakuUsaha> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriPelakuUsahaData> cq = cb.createQuery(KategoriPelakuUsahaData.class);
            Root<KategoriPelakuUsahaData> root = cq.from(KategoriPelakuUsahaData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_kategori_skala_usaha" -> daftarPredicate.add(cb.equal(root.get("skalaUsaha").get("id"), filter.getValue()));
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
                        case "kategori_skala_usaha" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("skalaUsaha").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("skalaUsaha").get("nama")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KategoriPelakuUsahaData> typedQuery;	

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
                            .map(d -> convertKategoriPelakuUsahaDataToKategoriPelakuUsaha(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriPelakuUsahaData.findAll", KategoriPelakuUsahaData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriPelakuUsahaDataToKategoriPelakuUsaha(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriPelakuUsahaData> root = cq.from(KategoriPelakuUsahaData.class);		

        // where clause
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        if( f != null) {
            Iterator<Filter> iterFilter = f.iterator();

            while (iterFilter.hasNext()) {
                Filter filter = (Filter) iterFilter.next();

                switch (filter.getField_name()) {
                    case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                    case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    case "id_kategori_skala_usaha" -> daftarPredicate.add(cb.equal(root.get("skalaUsaha").get("id"), filter.getValue()));
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
    
    private KategoriPelakuUsaha convertKategoriPelakuUsahaDataToKategoriPelakuUsaha(KategoriPelakuUsahaData d) {
        KategoriPelakuUsaha kategoriPelakuUsaha = null;
		
        if(d != null) {
            KategoriSkalaUsahaData kategoriSkalaUsahaData = d.getSkalaUsaha();
            kategoriPelakuUsaha = new KategoriPelakuUsaha(
                d.getId(), 
                d.getNama(), 
                new KategoriSkalaUsaha(
                    kategoriSkalaUsahaData.getId(), 
                    kategoriSkalaUsahaData.getNama(), 
                    kategoriSkalaUsahaData.getSingkatan(),
                    kategoriSkalaUsahaData.getKeterangan()
                )
            );
        }

        return kategoriPelakuUsaha;	
    }
    
    private KategoriPelakuUsahaData convertKategoriPelakuUsahaToKategoriPelakuUsahaData(KategoriPelakuUsaha t) {
        KategoriPelakuUsahaData kategoriPelakuUsahaData = null;
		
        if(t != null) {
            kategoriPelakuUsahaData = new KategoriPelakuUsahaData();
            kategoriPelakuUsahaData.setId(t.getId());
            kategoriPelakuUsahaData.setNama(t.getNama());
            KategoriSkalaUsahaData kategoriSkalaUsahaData = new KategoriSkalaUsahaData();
            kategoriSkalaUsahaData.setId(t.getKategori_skala_usaha().getId());
            kategoriPelakuUsahaData.setSkalaUsaha(kategoriSkalaUsahaData);
        }

        return kategoriPelakuUsahaData;
    }

}
