package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
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


public class DesaRepositoryJPA implements Repository<Desa, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public DesaRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }    

    @Override
    public Desa save(Desa t) throws SQLException {
        try {
            DesaData desaData = convertDesaToDesaData(t);
            entityManager.persist(desaData);
            entityManager.flush();             
            return convertDesaDataToDesa(desaData);  
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kecamatan harus ada di table kecamatan dan berupa bilangan dengan panjang 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data desa");
        }    
    }

    @Override
    public Desa update(Desa t) throws SQLException {
        
        try {
            DesaData desaData = convertDesaToDesaData(t);  
            desaData = entityManager.merge(desaData);
            return convertDesaDataToDesa(desaData);   
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kecamatan harus ada di table kecamatan dan berupa bilangan dengan panjang 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data desa");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            DesaData desaData = entityManager.find(DesaData.class, id);
            if(desaData != null) {
                entityManager.remove(desaData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("desa dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kecamatan harus ada di table kecamatan dan berupa bilangan dengan panjang 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public Desa updateId(String idLama, Desa t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("DesaData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id desa");
            }
        }  
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id kecamatan harus ada di table kecamatan dan berupa bilangan dengan panjang 10 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id desa");
        }
    }

    @Override
    public List<Desa> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<DesaData> cq = cb.createQuery(DesaData.class);
            Root<DesaData> root = cq.from(DesaData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "id_kecamatan" -> daftarPredicate.add(cb.equal(root.get("kecamatan").get("id"), filter.getValue()));
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
                        case "id_kecamatan" -> {
                            if(sort.getValue().equals("ASC")) {
                                cq.orderBy(cb.asc(root.get("kecamatan").get("id")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("kecamatan").get("id")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<DesaData> typedQuery;	

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
                    .map(d -> convertDesaDataToDesa(d))
                    .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("DesaData.findAll", DesaData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertDesaDataToDesa(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<DesaData> root = cq.from(DesaData.class);		

        // where clause
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        if( f != null) {
            Iterator<Filter> iterFilter = f.iterator();

            while (iterFilter.hasNext()) {
                Filter filter = (Filter) iterFilter.next();

                switch (filter.getField_name()) {
                    case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                    case "nama" -> daftarPredicate.add(cb.like(cb.lower(root.get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    case "id_kecamatan" -> daftarPredicate.add(cb.equal(root.get("kecamatan").get("id"), filter.getValue()));
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
    
    private Desa convertDesaDataToDesa(DesaData d) {
        Desa desa = null;
		
        if(d != null) {
            desa = new Desa(d.getId(), d.getNama(), d.getKecamatan().getId());
        }

        return desa;	
    }
    
    private DesaData convertDesaToDesaData(Desa t) {
        DesaData desaData = null;
		
        if(t != null) {
            KecamatanData kecamatanData = new KecamatanData(t.getId_kecamatan());
            desaData = new DesaData();
            desaData.setId(t.getId());
            desaData.setNama(t.getNama());
            desaData.setKecamatan(kecamatanData);
        }

        return desaData;
    }

}
