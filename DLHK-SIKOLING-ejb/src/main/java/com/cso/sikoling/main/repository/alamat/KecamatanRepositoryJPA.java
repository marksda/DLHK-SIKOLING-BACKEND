package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
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


public class KecamatanRepositoryJPA implements Repository<Kecamatan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KecamatanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }    

    @Override
    public Kecamatan save(Kecamatan t) throws SQLException {
        try {
            KecamatanData kecamatanData = convertKecamatanToKecamatanData(t);
            entityManager.persist(kecamatanData);
            entityManager.flush();             
            return convertKecamatanDataToKecamatan(kecamatanData);  
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kabupaten harus ada di table kabupaten dan berupa bilangan dengan panjang 7 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kecamatan");
        }    
    }

    @Override
    public Kecamatan update(Kecamatan t) throws SQLException {
        
        try {
            KecamatanData kecamatanData = convertKecamatanToKecamatanData(t);  
            kecamatanData = entityManager.merge(kecamatanData);
            return convertKecamatanDataToKecamatan(kecamatanData);   
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kabupaten harus ada di table kabupaten dan berupa bilangan dengan panjang 7 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data propinsi");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KecamatanData kecamatanData = entityManager.find(KecamatanData.class, id);
            if(kecamatanData != null) {
                entityManager.remove(kecamatanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kecamatan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kabupaten harus ada di table kabupaten dan berupa bilangan dengan panjang 7 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public Kecamatan updateId(String idLama, Kecamatan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KecamatanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kecamatan");
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kabupaten harus ada di table kabupaten dan berupa bilangan dengan panjang 7 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kecamatan");
        }
    }

    @Override
    public List<Kecamatan> getDaftarData(QueryParamFilters q) {
        
        List<KecamatanData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KecamatanData> cq = cb.createQuery(KecamatanData.class);
            Root<KecamatanData> root = cq.from(KecamatanData.class);		

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
                        case "id_kabupaten" -> daftarPredicate.add(cb.equal(root.get("kabupaten").get("id"), filter.getValue()));
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
                        case "id_kabupaten" -> {
                            if(sort.getValue().equals("ASC")) {
                                cq.orderBy(cb.asc(root.get("kabupaten").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("kabupaten").get("id")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KecamatanData> typedQuery;	

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
                    .map(d -> convertKecamatanDataToKecamatan(d))
                    .collect(Collectors.toList());
            } 
        }
        else {
            hasil = entityManager.createNamedQuery("KecamatanData.findAll", KecamatanData.class)
                 .getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                 .map(d -> convertKecamatanDataToKecamatan(d))
                            .collect(Collectors.toList());
            }                 
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KecamatanData> root = cq.from(KecamatanData.class);		

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
                    case "id_kabupaten" -> daftarPredicate.add(cb.equal(root.get("kabupaten").get("id"), filter.getValue()));
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
    
    private Kecamatan convertKecamatanDataToKecamatan(KecamatanData d) {
        Kecamatan kecamatan = null;
		
        if(d != null) {
            kecamatan = new Kecamatan(
                    d.getId(), d.getNama(), 
                    d.getPropinsi().getId(),
                    d.getKabupaten().getId()
                );
        }

        return kecamatan;	
    }
    
    private KecamatanData convertKecamatanToKecamatanData(Kecamatan t) {
        KecamatanData kecamatanData = null;
		
        if(t != null) {            
            kecamatanData = new KecamatanData();
            kecamatanData.setId(t.getId());
            kecamatanData.setNama(t.getNama());
            PropinsiData propinsiData = new PropinsiData(t.getId_propinsi());
            kecamatanData.setPropinsi(propinsiData);
            KabupatenData kabupatenData = new KabupatenData(t.getId_kabupaten());
            kecamatanData.setKabupaten(kabupatenData);
        }

        return kecamatanData;
    }

}
