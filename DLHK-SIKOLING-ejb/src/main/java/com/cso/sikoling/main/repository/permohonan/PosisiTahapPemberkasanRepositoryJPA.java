package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.permohonan.PosisiTahapPemberkasan;
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


public class PosisiTahapPemberkasanRepositoryJPA implements Repository<PosisiTahapPemberkasan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PosisiTahapPemberkasanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public PosisiTahapPemberkasan save(PosisiTahapPemberkasan t) throws SQLException {   
        try {
            PosisiTahapPemberkasanData posisiTahapPemberkasanData = convertPosisiTahapPemberkasanToPosisiTahapPemberkasanData(t);
            entityManager.persist(posisiTahapPemberkasanData);
            entityManager.flush();             
            return convertPosisiTahapPemberkasanDataToPosisiTahapPemberkasan(posisiTahapPemberkasanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id posisi tahap pemberkasan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data posisi tahap pemberkasan");
        }        
    }

    @Override
    public PosisiTahapPemberkasan update(PosisiTahapPemberkasan t) throws SQLException {
        
        try {
            PosisiTahapPemberkasanData posisiTahapPemberkasanData = convertPosisiTahapPemberkasanToPosisiTahapPemberkasanData(t);  
            posisiTahapPemberkasanData = entityManager.merge(posisiTahapPemberkasanData);
            return convertPosisiTahapPemberkasanDataToPosisiTahapPemberkasan(posisiTahapPemberkasanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id posisi tahap pemberkasan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data posisi tahap pemberkasan");
        }
        
    }

    @Override
    public PosisiTahapPemberkasan updateId(String idLama, PosisiTahapPemberkasan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PosisiTahapPemberkasanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id posisi tahap pemberkasan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id posisi tahap pemberkasan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi id posisi tahap pemberkasan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            PosisiTahapPemberkasanData posisiTahapPemberkasanData = entityManager.find(PosisiTahapPemberkasanData.class, id);
            if(posisiTahapPemberkasanData != null) {
                entityManager.remove(posisiTahapPemberkasanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("posisi tahap pemberkasan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id posisi tahap pemberkasan harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<PosisiTahapPemberkasan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PosisiTahapPemberkasanData> cq = cb.createQuery(PosisiTahapPemberkasanData.class);
            Root<PosisiTahapPemberkasanData> root = cq.from(PosisiTahapPemberkasanData.class);		

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


            TypedQuery<PosisiTahapPemberkasanData> typedQuery;	

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
                            .map(d -> convertPosisiTahapPemberkasanDataToPosisiTahapPemberkasan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("PosisiTahapPemberkasanData.findAll", PosisiTahapPemberkasanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertPosisiTahapPemberkasanDataToPosisiTahapPemberkasan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PosisiTahapPemberkasanData> root = cq.from(PosisiTahapPemberkasanData.class);	
        
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
    
    private PosisiTahapPemberkasan convertPosisiTahapPemberkasanDataToPosisiTahapPemberkasan(PosisiTahapPemberkasanData d) {
        PosisiTahapPemberkasan posisiTahapPemberkasan = null;
		
        if(d != null) {
            posisiTahapPemberkasan = new PosisiTahapPemberkasan(d.getId(), d.getNama(), d.getKeterangan());
        }

        return posisiTahapPemberkasan;	
    }
    
    private PosisiTahapPemberkasanData convertPosisiTahapPemberkasanToPosisiTahapPemberkasanData(PosisiTahapPemberkasan t) {
        PosisiTahapPemberkasanData posisiTahapPemberkasanData = null;
		
        if(t != null) {
            posisiTahapPemberkasanData = new PosisiTahapPemberkasanData();
            String id = t.getId();
            posisiTahapPemberkasanData.setId(id != null ? id : getGenerateId());
            posisiTahapPemberkasanData.setNama(t.getNama());
            posisiTahapPemberkasanData.setKeterangan(t.getKeterangan());
        }

        return posisiTahapPemberkasanData;
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
