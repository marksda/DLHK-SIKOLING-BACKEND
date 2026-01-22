package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
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


public class KategoriPengurusPermohonanRepositoryJPA implements Repository<KategoriPengurusPermohonan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public KategoriPengurusPermohonanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public KategoriPengurusPermohonan save(KategoriPengurusPermohonan t) throws SQLException {   
        try {
            KategoriPengurusPermohonanData kategoriPengurusPermohonanData = convertKategoriPengurusPermohonanToKategoriPengurusPermohonanData(t);
            entityManager.persist(kategoriPengurusPermohonanData);
            entityManager.flush();             
            return convertKategoriPengurusPermohonanDataToKategoriPengurusPermohonan(kategoriPengurusPermohonanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori permohonan");
        }        
    }

    @Override
    public KategoriPengurusPermohonan update(KategoriPengurusPermohonan t) throws SQLException {
        
        try {
            KategoriPengurusPermohonanData kategoriPengurusPermohonanData = convertKategoriPengurusPermohonanToKategoriPengurusPermohonanData(t);  
            kategoriPengurusPermohonanData = entityManager.merge(kategoriPengurusPermohonanData);
            return convertKategoriPengurusPermohonanDataToKategoriPengurusPermohonan(kategoriPengurusPermohonanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pengurus permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data kategori permohonan");
        }
        
    }

    @Override
    public KategoriPengurusPermohonan updateId(String idLama, KategoriPengurusPermohonan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("KategoriPengurusPermohonanData.updateId");
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
            throw new SQLException("id kategori pengurus permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id kategori pengurus permohonan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            KategoriPengurusPermohonanData kategoriPengurusPermohonanData = entityManager.find(KategoriPengurusPermohonanData.class, id);
            if(kategoriPengurusPermohonanData != null) {
                entityManager.remove(kategoriPengurusPermohonanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("kategori pengurus permohonan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kategori pengurus permohonan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<KategoriPengurusPermohonan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<KategoriPengurusPermohonanData> cq = cb.createQuery(KategoriPengurusPermohonanData.class);
            Root<KategoriPengurusPermohonanData> root = cq.from(KategoriPengurusPermohonanData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
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
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<KategoriPengurusPermohonanData> typedQuery;	

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
                            .map(d -> convertKategoriPengurusPermohonanDataToKategoriPengurusPermohonan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("KategoriPengurusPermohonanData.findAll", KategoriPengurusPermohonanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertKategoriPengurusPermohonanDataToKategoriPengurusPermohonan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<KategoriPengurusPermohonanData> root = cq.from(KategoriPengurusPermohonanData.class);		

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
    
    private KategoriPengurusPermohonan convertKategoriPengurusPermohonanDataToKategoriPengurusPermohonan(KategoriPengurusPermohonanData d) {
        KategoriPengurusPermohonan kategoriPengurusPermohonan = null;
		
        if(d != null) {
            kategoriPengurusPermohonan = new KategoriPengurusPermohonan(d.getId(), d.getNama());
        }

        return kategoriPengurusPermohonan;	
    }
    
    private KategoriPengurusPermohonanData convertKategoriPengurusPermohonanToKategoriPengurusPermohonanData(KategoriPengurusPermohonan t) {
        KategoriPengurusPermohonanData kategoriPengurusPermohonanData = null;
		
        if(t != null) {
            kategoriPengurusPermohonanData = new KategoriPengurusPermohonanData();
            String id = t.getId();
            kategoriPengurusPermohonanData.setId(id != null ? id : getGenerateId());
            kategoriPengurusPermohonanData.setNama(t.getNama());
        }

        return kategoriPengurusPermohonanData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM KategoriPengurusPermohonanData m");

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
