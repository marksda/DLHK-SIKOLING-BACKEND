package com.cso.sikoling.main.repository.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.repository.alamat.DesaData;
import com.cso.sikoling.main.repository.alamat.KabupatenData;
import com.cso.sikoling.main.repository.alamat.KecamatanData;
import com.cso.sikoling.main.repository.alamat.PropinsiData;
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


public class PersonRepositoryJPA implements Repository<Person, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PersonRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Person save(Person t) throws SQLException {   
        try {
            PersonData personData = convertPersonToPersonData(t);
            entityManager.persist(personData);
            entityManager.flush();             
            return convertPersonDataToPerson(personData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id person harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data person");
        }        
    }

    @Override
    public Person update(Person t) throws SQLException {
        
        try {
            PersonData personData = convertPersonToPersonData(t);  
            personData = entityManager.merge(personData);
            return convertPersonDataToPerson(personData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id person harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data person");
        }
        
    }

    @Override
    public Person updateId(String idLama, Person t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PersonData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id person");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id person harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id person");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            PersonData personData = entityManager.find(PersonData.class, id);
            if(personData != null) {
                entityManager.remove(personData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("person dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id person harus bilangan dan panjang 2 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Person> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PersonData> cq = cb.createQuery(PersonData.class);
            Root<PersonData> root = cq.from(PersonData.class);		

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


            TypedQuery<PersonData> typedQuery;	

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
                            .map(d -> convertPersonDataToPerson(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("PersonData.findAll", PersonData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertPersonDataToPerson(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PersonData> root = cq.from(PersonData.class);		

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
    
    private Person convertPersonDataToPerson(PersonData d) {
        
        Person person = null;
		
        if(d != null) {
            Propinsi propinsi = d.getPropinsi() != null ? 
                    new Propinsi(
                        d.getPropinsi().getId(), d.getPropinsi().getNama()
                    ) 
                    : null;
            Kabupaten kabupaten = d.getKabupaten() != null ? 
                    new Kabupaten(
                        d.getKabupaten().getId(), 
                        d.getKabupaten().getNama(), 
                        propinsi != null ? propinsi.getId() : null
                    ) 
                    : null;
            Kecamatan kecamatan = d.getKecamatan() != null ? 
                    new Kecamatan(
                        d.getKecamatan().getId(), 
                        d.getKecamatan().getNama(), 
                        propinsi != null ? propinsi.getId() : null,
                        kabupaten != null ? kabupaten.getId() : null
                    ) 
                    : null;
            Desa desa = d.getDesa() != null ? 
                    new Desa(
                        d.getDesa().getId(), 
                        d.getDesa().getNama(), 
                        kecamatan != null ? kecamatan.getId() : null
                    ) 
                    : null;            
            Alamat alamat = new Alamat(propinsi, kabupaten, kecamatan, desa, d.getDetailAlamat());            
            Kontak kontak = new Kontak(d.getTelepone(), d.getFax(), d.getEmail());
            JenisKelamin jenisKelamin = new JenisKelamin(d.getSex().getId(), d.getSex().getNama());
            person = new Person(d.getId(), d.getNama(), jenisKelamin, alamat, d.getScanKtp(), kontak, d.getIsValidated());
        }

        return person;	
        
    }
    
    private PersonData convertPersonToPersonData(Person t) {
        
        PersonData personData = null;
		
        if(t != null) {
            personData = new PersonData();
            personData.setId(t.getId());
            personData.setNama(t.getNama());
            personData.setTelepone(t.getKontak().getTelepone());
            personData.setEmail(t.getKontak().getEmail());
            personData.setFax(t.getKontak().getFax());
            personData.setScanKtp(t.getScanKTP());            
            JenisKelaminData jenisKelaminData = new JenisKelaminData();
            jenisKelaminData.setId(t.getJenisKelamin().getId());
            jenisKelaminData.setNama(t.getJenisKelamin().getNama());
            personData.setSex(jenisKelaminData);
            personData.setDetailAlamat(t.getAlamat().getKeterangan());
            personData.setDesa(
                new DesaData(t.getAlamat().getDesa().getId(), t.getAlamat().getDesa().getNama())
            );
            personData.setKecamatan(
                new KecamatanData(t.getAlamat().getKecamatan().getId(), t.getAlamat().getKecamatan().getNama())
            );
            personData.setKabupaten(
                new KabupatenData(t.getAlamat().getKabupaten().getId(), t.getAlamat().getKabupaten().getNama())
            );
            personData.setPropinsi(
                new PropinsiData(t.getAlamat().getPropinsi().getId(), t.getAlamat().getPropinsi().getNama())
            );
        }

        return personData;
        
    }

}
