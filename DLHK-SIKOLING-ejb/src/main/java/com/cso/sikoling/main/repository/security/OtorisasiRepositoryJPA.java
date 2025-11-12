package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.repository.alamat.DesaData;
import com.cso.sikoling.main.repository.alamat.KabupatenData;
import com.cso.sikoling.main.repository.alamat.KecamatanData;
import com.cso.sikoling.main.repository.alamat.PropinsiData;
import com.cso.sikoling.main.repository.person.JenisKelaminData;
import com.cso.sikoling.main.repository.person.PersonData;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class OtorisasiRepositoryJPA implements Repository<Otorisasi, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public OtorisasiRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Otorisasi save(Otorisasi t) throws SQLException {   
        try {
            OtorisasiData autorisasiData = convertOtorisasiToOtorisasiData(t);
            entityManager.persist(autorisasiData);
            entityManager.flush();             
            return convertOtorisasiDataToOtorisasi(autorisasiData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data autorisasi");
        }        
    }

    @Override
    public Otorisasi update(Otorisasi t) throws SQLException {
        
        try {
            OtorisasiData autorisasiData = convertOtorisasiToOtorisasiData(t);  
            autorisasiData = entityManager.merge(autorisasiData);
            return convertOtorisasiDataToOtorisasi(autorisasiData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data autorisasi");
        }
        
    }

    @Override
    public Otorisasi updateId(String idLama, Otorisasi t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("OtorisasiData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id autorisasi");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id autorisasi");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            OtorisasiData autorisasiData = entityManager.find(OtorisasiData.class, id);
            if(autorisasiData != null) {
                entityManager.remove(autorisasiData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("autorisasi dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id autorisasi harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Otorisasi> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<OtorisasiData> cq = cb.createQuery(OtorisasiData.class);
            Root<OtorisasiData> root = cq.from(OtorisasiData.class);		

            // where clause
            if(q.getFields_filter() != null) {
                Iterator<Filter> iterFilter = q.getFields_filter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "tanggal_registrasi" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
                        case "id_user" -> daftarPredicate.add(cb.equal(root.get("idUser"), filter.getValue()));
                        case "id_hak_akses" -> daftarPredicate.add(cb.equal(root.get("hakAkses").get("id"), filter.getValue()));
                        case "rentang_tanggal" -> {
                            Jsonb jsonb = JsonbBuilder.create();
                            JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");                            
                            Date fromDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("from"), formatter));
                            Date toDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("to"), formatter));
                            
                            daftarPredicate.add(
                                cb.between(
                                    root.get("tanggalRegistrasi"), 
                                    fromDate, 
                                    toDate
                                )
                            );
                        }
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
                        case "user_name" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("userName")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("userName")));
                            }
                        }
                        case "hak_akses" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("hakAkses").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("hakAkses").get("nama")));
                            }
                        }
                        case "tanggal_registrasi" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("tanggalRegistrasi")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("tanggalRegistrasi")));
                            }
                        }
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<OtorisasiData> typedQuery;	

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
                            .map(d -> convertOtorisasiDataToOtorisasi(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("OtorisasiData.findAll", OtorisasiData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertOtorisasiDataToOtorisasi(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<OtorisasiData> root = cq.from(OtorisasiData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "user_name" -> daftarPredicate.add(cb.like(cb.lower(root.get("userName")), "%"+filter.getValue().toLowerCase()+"%"));
                case "tanggal_registrasi" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
                case "id_user" -> daftarPredicate.add(cb.equal(root.get("idUser"), filter.getValue()));
                case "id_hak_akses" -> daftarPredicate.add(cb.equal(root.get("hakAkses").get("id"), filter.getValue()));
                case "rentang_tanggal" -> {
                    Jsonb jsonb = JsonbBuilder.create();
                    JsonObject d = jsonb.fromJson(filter.getValue(), JsonObject.class);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");                            
                    Date fromDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("from"), formatter));
                    Date toDate = java.sql.Date.valueOf(LocalDate.parse(d.getString("to"), formatter));

                    daftarPredicate.add(
                        cb.between(
                            root.get("tanggalRegistrasi"), 
                            fromDate, 
                            toDate
                        )
                    );
                }
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
    
    private Otorisasi convertOtorisasiDataToOtorisasi(OtorisasiData d) {
        Otorisasi otorisasi = null;
		
        if(d != null) {
            PersonData personData = d.getPerson();
            JenisKelaminData jenisKelaminData = personData != null ?
                                                personData.getSex() : null;
            JenisKelamin jenisKelamin = jenisKelaminData != null ?
                                        new JenisKelamin(
                                                jenisKelaminData.getId(), 
                                                jenisKelaminData.getNama()
                                        ) : null;
            
            PropinsiData propinsiData = personData != null ?
                                        personData.getPropinsi() : null;
            Propinsi propinsi = propinsiData != null ?
                    new Propinsi(
                        propinsiData.getId(), 
                        propinsiData.getNama()
                    )
                    : null;
            
            KabupatenData kabupatenData = personData != null ?
                                        personData.getKabupaten() : null;
            Kabupaten kabupaten = kabupatenData != null ?
                    new Kabupaten(
                        kabupatenData.getId(), 
                        kabupatenData.getNama(), 
                        propinsi != null ? propinsi.getId() : null
                    )
                    : null;
            
            KecamatanData kecamatanData = personData != null ?
                                        personData.getKecamatan() : null;
            Kecamatan kecamatan = kecamatanData != null ?
                    new Kecamatan(
                        kecamatanData.getId(), 
                        kecamatanData.getNama(), 
                        propinsi != null ? propinsi.getId() : null,
                        kabupaten != null ? kabupaten.getId() : null
                    )
                    : null;
            
            DesaData desaData = personData != null ?
                                personData.getDesa() : null;
            Desa desa = desaData != null ?
                    new Desa(
                        desaData.getId(), 
                        desaData.getNama(), 
                        propinsi != null ? propinsi.getId() : null,
                        kabupaten != null ? kabupaten.getId() : null,
                        kecamatan != null ? kecamatan.getId() : null
                    )
                    : null;
                    
            Alamat alamat = new Alamat(
                propinsi, 
                kabupaten, 
                kecamatan, 
                desa, 
                personData != null ? personData.getDetailAlamat() : null
            );        
            
            Kontak kontak = new Kontak(
                personData != null ? personData.getTelepone() : null, 
                null, 
                personData != null ? personData.getEmail() : null
            );
            
            
            Person person = personData != null ?
                    new Person(
                        personData.getId(), 
                        personData.getNama(), 
                        jenisKelamin, 
                        alamat, 
                        personData.getScanKtp(), 
                        kontak, 
                        personData.getIsValidated(), 
                        personData.getTanggalRegistrasi()
                    ) : null;
            
            HakAksesData hakAksesData = d.getHakAkses();
            
            HakAkses hakAkses = hakAksesData != null ?
                    new HakAkses(
                        hakAksesData.getId(), 
                        hakAksesData.getNama(), 
                        hakAksesData.getKeterangan()
                    ) : null;
            
            otorisasi = new Otorisasi(
                    d.getId(), 
                    d.getIdUser(), 
                    d.getIsVerified(), 
                    d.getUserName(), 
                    d.getTanggalRegistrasi(), 
                    hakAkses, 
                    person
                );
        }

        return otorisasi;	
    }
    
    private OtorisasiData convertOtorisasiToOtorisasiData(Otorisasi t) {
        OtorisasiData autorisasiData = null;
		
        if(t != null) {
            autorisasiData = new OtorisasiData();
            autorisasiData.setId(t.getId());
            autorisasiData.setIdUser(t.getId_user());
            autorisasiData.setIsVerified(t.getIs_verified());
            autorisasiData.setUserName(t.getUser_name());
            autorisasiData.setTanggalRegistrasi(t.getTanggal_registrasi());
            HakAkses hakAkses = t.getHak_akses();
            HakAksesData hakAksesData = new HakAksesData();
            hakAksesData.setId(hakAkses != null ? hakAkses.getId() : null);
            autorisasiData.setHakAkses(hakAksesData);
            Person person = t.getPerson();
            autorisasiData.setPerson(new PersonData(person.getId()));
        }

        return autorisasiData;
    }

}
