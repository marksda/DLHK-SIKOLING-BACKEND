package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
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
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;


public class PelakuUsahaRepositoryJPA implements Repository<PelakuUsaha, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PelakuUsahaRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public PelakuUsaha save(PelakuUsaha t) throws SQLException {   
        try {
            DetailPelakuUsahaData pelakuUsahaData = convertPelakuUsahaToPelakuUsahaData(t);
            entityManager.persist(pelakuUsahaData);
            entityManager.flush();             
            return convertPelakuUsahaDataToPelakuUsaha(pelakuUsahaData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pelaku usaha harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data pelaku usaha");
        }        
    }

    @Override
    public PelakuUsaha update(PelakuUsaha t) throws SQLException {
        
        try {
            DetailPelakuUsahaData pelakuUsahaData = convertPelakuUsahaToPelakuUsahaData(t);  
            pelakuUsahaData = entityManager.merge(pelakuUsahaData);
            return convertPelakuUsahaDataToPelakuUsaha(pelakuUsahaData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pelaku usaha harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data pelaku usaha");
        }
        
    }

    @Override
    public PelakuUsaha updateId(String idLama, PelakuUsaha t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PelakuUsahaData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id pelaku usaha");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pelaku usaha harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id pelaku usaha");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            DetailPelakuUsahaData pelakuUsahaData = entityManager.find(DetailPelakuUsahaData.class, id);
            if(pelakuUsahaData != null) {
                entityManager.remove(pelakuUsahaData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("pelaku usaha dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pelaku usaha harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<PelakuUsaha> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<DetailPelakuUsahaData> cq = cb.createQuery(DetailPelakuUsahaData.class);
            Root<DetailPelakuUsahaData> root = cq.from(DetailPelakuUsahaData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_kategori_pelaku_usaha" -> daftarPredicate.add(cb.equal(root.get("kategoriPelakuUsaha").get("id"), filter.getValue()));
                        case "id_kategori_skala_usaha" -> daftarPredicate.add(cb.equal(root.get("kategoriPelakuUsaha").get("skalaUsaha").get("id"), filter.getValue()));
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


            TypedQuery<DetailPelakuUsahaData> typedQuery;	

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
                            .map(d -> convertPelakuUsahaDataToPelakuUsaha(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("DetailPelakuUsahaData.findAll", DetailPelakuUsahaData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertPelakuUsahaDataToPelakuUsaha(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<DetailPelakuUsahaData> root = cq.from(DetailPelakuUsahaData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                case "id_kategori_pelaku_usaha" -> daftarPredicate.add(cb.equal(root.get("kategoriPelakuUsaha").get("id"), filter.getValue()));
                case "id_kategori_skala_usaha" -> daftarPredicate.add(cb.equal(root.get("kategoriPelakuUsaha").get("skalaUsaha").get("id"), filter.getValue()));
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
    
    private PelakuUsaha convertPelakuUsahaDataToPelakuUsaha(DetailPelakuUsahaData d) {
        PelakuUsaha pelakuUsaha = null;
		
        if(d != null) {
            KategoriPelakuUsahaData kategoriPelakuUsahaData = d.getKategoriPelakuUsaha();
            KategoriPelakuUsaha kategoriPelakuUsaha = kategoriPelakuUsahaData != null ?
                    new KategoriPelakuUsaha(
                            kategoriPelakuUsahaData.getId(), 
                            kategoriPelakuUsahaData.getNama(), 
                            kategoriPelakuUsahaData.getSkalaUsaha().getId()) : null;
            
            pelakuUsaha = new PelakuUsaha(
                d.getId(), 
                d.getNama(), 
                d.getSingkatan(),
                kategoriPelakuUsaha
            );
        }

        return pelakuUsaha;	
    }
    
    private DetailPelakuUsahaData convertPelakuUsahaToPelakuUsahaData(PelakuUsaha t) {
        DetailPelakuUsahaData pelakuUsahaData = null;
		
        if(t != null) {
            pelakuUsahaData = new DetailPelakuUsahaData();
            pelakuUsahaData.setId(t.getId());
            pelakuUsahaData.setNama(t.getNama());
            pelakuUsahaData.setSingkatan(t.getSingkatan());
        }

        return pelakuUsahaData;
    }

}
