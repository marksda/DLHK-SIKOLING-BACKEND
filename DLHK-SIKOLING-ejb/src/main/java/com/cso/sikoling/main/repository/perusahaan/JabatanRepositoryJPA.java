package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
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


public class JabatanRepositoryJPA implements Repository<Jabatan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public JabatanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Jabatan save(Jabatan t) throws SQLException {   
        try {
            JabatanData jabatanData = convertJabatanToJabatanData(t);
            entityManager.persist(jabatanData);
            entityManager.flush();  
            
            return convertJabatanDataToJabatan(jabatanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jabatan harus bilangan dan panjang 3 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jabatan");
        }        
    }

    @Override
    public Jabatan update(Jabatan t) throws SQLException {
        
        try {
            JabatanData jabatanData = convertJabatanToJabatanData(t);  
            jabatanData = entityManager.merge(jabatanData);
            return convertJabatanDataToJabatan(jabatanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jabatan harus bilangan dan panjang 3 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data jabatan");
        }
        
    }

    @Override
    public Jabatan updateId(String idLama, Jabatan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("JabatanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id jabatan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jabatan harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id jabatan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            JabatanData jabatanData = entityManager.find(JabatanData.class, id);
            if(jabatanData != null) {
                entityManager.remove(jabatanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("jabatan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id jabatan harus bilangan dan panjang 6 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Jabatan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<JabatanData> cq = cb.createQuery(JabatanData.class);
            Root<JabatanData> root = cq.from(JabatanData.class);		

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


            TypedQuery<JabatanData> typedQuery;	

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
                            .map(d -> convertJabatanDataToJabatan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("JabatanData.findAll", JabatanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertJabatanDataToJabatan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<JabatanData> root = cq.from(JabatanData.class);		

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
    
    private Jabatan convertJabatanDataToJabatan(JabatanData d) {
        Jabatan jabatan = null;
		
        if(d != null) {            
            jabatan = new Jabatan(
                d.getId(), 
                d.getNama()
            );
        }

        return jabatan;	
    }
    
    private JabatanData convertJabatanToJabatanData(Jabatan t) {
        JabatanData jabatanData = null;
		
        if(t != null) {
            jabatanData = new JabatanData();
            String id = t.getId();
            jabatanData.setId(id != null ? id : getGenerateId());
            jabatanData.setNama(t.getNama());
        }

        return jabatanData;
    }
    
    private String getGenerateId() {
        String hasil;

        Query q = entityManager.createQuery("SELECT MAX(m.id) FROM JabatanData m");

        try {
                hasil = (String) q.getSingleResult();
                Long idBaru = Long.parseLong(hasil)  + 1;
                hasil = LPad(Long.toString(idBaru), 3, '0');
                return hasil;
        } catch (NumberFormatException e) {	
                hasil = "001";			
                return hasil;
        }		
    }
    
    private String LPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

}
