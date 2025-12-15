package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
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


public class KategoriPermohonanRepositoryJPA implements Repository<KategoriPermohonan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriPermohonanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriPermohonan save(KategoriPermohonan t) throws SQLException {   
        try {
            KategoriPermohonanData kategoriPermohonanData = convertKategoriPermohonanToKategoriPermohonanData(t);
            entityManager.persist(kategoriPermohonanData);
            entityManager.flush();             
            return convertKategoriPermohonanDataToKategoriPermohonan(kategoriPermohonanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori permohonan");
        }        
    }

    @Override
    public KategoriPermohonan update(KategoriPermohonan t) throws SQLException {
        
        try {
            KategoriPermohonanData kategoriPermohonanData = convertKategoriPermohonanToKategoriPermohonanData(t);  
            kategoriPermohonanData = entityManager.merge(kategoriPermohonanData);
            return convertKategoriPermohonanDataToKategoriPermohonan(kategoriPermohonanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori permohonan");
        }
        
    }

    @Override
    public KategoriPermohonan updateId(String idLama, KategoriPermohonan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriPermohonanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id kategori permohonan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori permohonan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriPermohonanData kategoriPermohonanData = entityManager.find(KategoriPermohonanData.class, id);
            if(kategoriPermohonanData != null) {
                entityManager.remove(kategoriPermohonanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori permohonan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriPermohonan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriPermohonanData> cq = cb.createQuery(KategoriPermohonanData.class);
            Root<KategoriPermohonanData> root = cq.from(KategoriPermohonanData.class);		

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


            TypedQuery<KategoriPermohonanData> typedQuery;	

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
                            .map(d -> convertKategoriPermohonanDataToKategoriPermohonan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriPermohonanData.findAll", KategoriPermohonanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriPermohonanDataToKategoriPermohonan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriPermohonanData> root = cq.from(KategoriPermohonanData.class);		

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
    
    private KategoriPermohonan convertKategoriPermohonanDataToKategoriPermohonan(KategoriPermohonanData d) {
        KategoriPermohonan kategoriPermohonan = null;
		
        if(d != null) {
            kategoriPermohonan = new KategoriPermohonan(d.getId(), d.getNama(), d.getIdLama());
        }

        return kategoriPermohonan;	
    }
    
    private KategoriPermohonanData convertKategoriPermohonanToKategoriPermohonanData(KategoriPermohonan t) {
        KategoriPermohonanData kategoriPermohonanData = null;
		
        if(t != null) {
            kategoriPermohonanData = new KategoriPermohonanData();
            String id = t.getId();
            kategoriPermohonanData.setId(id != null ? id : getGenerateId());
            kategoriPermohonanData.setNama(t.getNama());
            kategoriPermohonanData.setIdLama(t.getId_lama());
        }

        return kategoriPermohonanData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM KategoriPermohonanData m");

        try {
                hasil = (String) q.getSingleResult();
                Long idBaru = Long.parseLong(hasil)  + 1;
                hasil = LPad(Long.toString(idBaru), 2, '0');
                return hasil;
        } catch (NumberFormatException e) {	
                hasil = "01";			
                return hasil;
        }		
    }
    
    private String LPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

}
