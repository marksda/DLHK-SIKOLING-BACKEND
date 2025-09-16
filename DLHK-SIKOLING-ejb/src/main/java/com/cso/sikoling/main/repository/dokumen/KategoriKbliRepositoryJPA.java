package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
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


public class KategoriKbliRepositoryJPA implements Repository<KategoriKbli, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriKbliRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriKbli save(KategoriKbli t) throws SQLException {   
        try {
            KategoriKbliData kategoriKbliData = convertKategoriKbliToKategoriKbliData(t);
            entityManager.persist(kategoriKbliData);
            entityManager.flush();             
            return convertKategoriKbliDataToKategoriKbli(kategoriKbliData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori kbli harus abjad dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori kbli");
        }        
    }

    @Override
    public KategoriKbli update(KategoriKbli t) throws SQLException {
        
        try {
            KategoriKbliData kategoriKbliData = convertKategoriKbliToKategoriKbliData(t);  
            kategoriKbliData = entityManager.merge(kategoriKbliData);
            return convertKategoriKbliDataToKategoriKbli(kategoriKbliData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori kbli harus abjad dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori kbli");
        }
        
    }

    @Override
    public KategoriKbli updateId(String idLama, KategoriKbli t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriKbliData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kategori kbli");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori kbli harus abjad dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori kbli");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriKbliData kategoriKbliData = entityManager.find(KategoriKbliData.class, id);
            if(kategoriKbliData != null) {
                entityManager.remove(kategoriKbliData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori kbli dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori kbli harus abjad dan panjang 1 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriKbli> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriKbliData> cq = cb.createQuery(KategoriKbliData.class);
            Root<KategoriKbliData> root = cq.from(KategoriKbliData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_versi" -> daftarPredicate.add(cb.equal(root.get("versiKbli").get("id"), filter.getValue()));
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
                        case "id_versi" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("versiKbli").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("versiKbli").get("id")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KategoriKbliData> typedQuery;	

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
                            .map(d -> convertKategoriKbliDataToKategoriKbli(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriKbliData.findAll", KategoriKbliData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriKbliDataToKategoriKbli(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriKbliData> root = cq.from(KategoriKbliData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                case "id_versi" -> daftarPredicate.add(cb.equal(root.get("versiKbli").get("id"), filter.getValue()));
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
    
    private KategoriKbli convertKategoriKbliDataToKategoriKbli(KategoriKbliData d) {
        KategoriKbli kategoriKbli = null;
		
        if(d != null) {
            VersiKbliData versiKbliData = d.getVersiKbli();
            VersiKbli versiKbli = versiKbliData != null ?
                    new VersiKbli(versiKbliData.getId(), versiKbliData.getNama()) : null;
            kategoriKbli = new KategoriKbli(d.getId(), d.getNama(), versiKbli);
        }

        return kategoriKbli;	
    }
    
    private KategoriKbliData convertKategoriKbliToKategoriKbliData(KategoriKbli t) {
        KategoriKbliData kategoriKbliData = null;
		
        if(t != null) {
            kategoriKbliData = new KategoriKbliData();
            kategoriKbliData.setId(t.getId());
            kategoriKbliData.setNama(t.getNama());
            VersiKbliData versiKbliData = new VersiKbliData();
            versiKbliData.setId(t.getVersiKbli().getId());
            versiKbliData.setNama(t.getVersiKbli().getNama());
            kategoriKbliData.setVersiKbli(versiKbliData);
        }

        return kategoriKbliData;
    }

}
