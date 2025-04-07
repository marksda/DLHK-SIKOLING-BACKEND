package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
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


public class DokumenRepositoryJPA implements Repository<Dokumen, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public DokumenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Dokumen save(Dokumen t) throws SQLException {   
        try {
            DokumenData dokumenData = convertDokumenToDokumenData(t);
            entityManager.persist(dokumenData);
            entityManager.flush();             
            return convertDokumenDataToDokumen(dokumenData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id dokumen harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data dokumen");
        }        
    }

    @Override
    public Dokumen update(Dokumen t) throws SQLException {
        
        try {
            DokumenData dokumenData = convertDokumenToDokumenData(t);  
            dokumenData = entityManager.merge(dokumenData);
            return convertDokumenDataToDokumen(dokumenData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id dokumen harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data dokumen");
        }
        
    }

    @Override
    public Dokumen updateId(String idLama, Dokumen t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("DokumenData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id dokumen");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id dokumen harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id dokumen");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            DokumenData dokumenData = entityManager.find(DokumenData.class, id);
            if(dokumenData != null) {
                entityManager.remove(dokumenData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("dokumen dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id dokumen harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Dokumen> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<DokumenData> cq = cb.createQuery(DokumenData.class);
            Root<DokumenData> root = cq.from(DokumenData.class);		

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


            TypedQuery<DokumenData> typedQuery;	

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
                            .map(d -> convertDokumenDataToDokumen(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("DokumenData.findAll", DokumenData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertDokumenDataToDokumen(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<DokumenData> root = cq.from(DokumenData.class);		

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
    
    private Dokumen convertDokumenDataToDokumen(DokumenData d) {
        Dokumen dokumen = null;
		
        if(d != null) {
            dokumen = new Dokumen(d.getId(), d.getNama(), d.getSingkatan(), d.getIdLama());
        }

        return dokumen;	
    }
    
    private DokumenData convertDokumenToDokumenData(Dokumen t) {
        DokumenData dokumenData = null;
		
        if(t != null) {
            dokumenData = new DokumenData();
            dokumenData.setId(t.getId());
            dokumenData.setNama(t.getNama());
            dokumenData.setSingkatan(t.getSingkatan());
            dokumenData.setIdLama(t.getId_lama());
        }

        return dokumenData;
    }

}
