package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.Kbli;
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


public class KbliRepositoryJPA implements Repository<Kbli, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KbliRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Kbli save(Kbli t) throws SQLException {   
        try {
            KbliData kbliData = convertKbliToKbliData(t);
            entityManager.persist(kbliData);
            entityManager.flush();             
            return convertKbliDataToKbli(kbliData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kbli harus bilangan 5 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kbli");
        }        
    }

    @Override
    public Kbli update(Kbli t) throws SQLException {
        
        try {
            KbliData kbliData = convertKbliToKbliData(t);  
            kbliData = entityManager.merge(kbliData);
            return convertKbliDataToKbli(kbliData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kbli harus bilangan 5 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kbli");
        }
        
    }

    @Override
    public Kbli updateId(String idLama, Kbli t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KbliData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kbli");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kbli harus bilangan 5 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kbli");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KbliData kbliData = entityManager.find(KbliData.class, id);
            if(kbliData != null) {
                entityManager.remove(kbliData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("Kbli dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kbli harus bilangan 5 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Kbli> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KbliData> cq = cb.createQuery(KbliData.class);
            Root<KbliData> root = cq.from(KbliData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.like(cb.lower(root.get("id")), filter.getValue()+"%"));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_versi" -> daftarPredicate.add(cb.equal(root.get("versiKbli").get("id"), filter.getValue()));
                        case "id_kategori" -> daftarPredicate.add(cb.equal(root.get("kategoriKbli").get("id"), filter.getValue()));
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
                        case "id_kategori" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("kategoriKbli").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("kategoriKbli").get("id")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KbliData> typedQuery;	

            if( q.isIs_paging()) { 
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
                            .map(d -> convertKbliDataToKbli(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KbliData.findAll", KbliData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKbliDataToKbli(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KbliData> root = cq.from(KbliData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.like(cb.lower(root.get("id")), filter.getValue()+"%"));
                case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                case "id_versi" -> daftarPredicate.add(cb.equal(root.get("versiKbli").get("id"), filter.getValue()));
                case "id_kategori" -> daftarPredicate.add(cb.equal(root.get("kategoriKbli").get("id"), filter.getValue()));
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
    
    private Kbli convertKbliDataToKbli(KbliData d) {
        Kbli kbli = null;
		
        if(d != null) {
            VersiKbliData versiKbliData = d.getVersiKbli();
            VersiKbli versiKbli = versiKbliData != null ?
                    new VersiKbli(versiKbliData.getId(), versiKbliData.getNama()) : null;
            
            KategoriKbliData kategoriKbliData = d.getKategoriKbli();
            KategoriKbli kategoriKbli = kategoriKbliData != null ?
                    new KategoriKbli(
                            kategoriKbliData.getId(), 
                            kategoriKbliData.getNama(), 
                            kategoriKbliData.getKode(),
                            versiKbli
                    ) : null;
            
            kbli = new Kbli(d.getId(), d.getNama(), versiKbli, kategoriKbli);
        }

        return kbli;	
    }
    
    private KbliData convertKbliToKbliData(Kbli t) {
        KbliData kbliData = null;
		
        if(t != null) {
            kbliData = new KbliData();
            kbliData.setId(t.getId());
            kbliData.setNama(t.getNama());            
            VersiKbliData versiKbliData = new VersiKbliData();
            versiKbliData.setId(t.getVersiKbli().getId());
            versiKbliData.setNama(t.getVersiKbli().getNama());            
            kbliData.setVersiKbli(versiKbliData);            
            KategoriKbliData kategoriKbliData = new KategoriKbliData();
            kategoriKbliData.setId(t.getKategoriKbli().getId());
            kategoriKbliData.setNama(t.getKategoriKbli().getNama());
            kategoriKbliData.setKode(t.getKategoriKbli().getKode());
            kategoriKbliData.setVersiKbli(versiKbliData);            
            kbliData.setKategoriKbli(kategoriKbliData);
        }

        return kbliData;
    }

}
