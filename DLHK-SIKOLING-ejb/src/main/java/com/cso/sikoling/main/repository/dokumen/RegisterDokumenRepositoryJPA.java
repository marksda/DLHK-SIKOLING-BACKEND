package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.dokumen.MasterDokumen;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.entity.dokumen.StatusDokumen;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.repository.alamat.DesaData;
import com.cso.sikoling.main.repository.alamat.KabupatenData;
import com.cso.sikoling.main.repository.alamat.KecamatanData;
import com.cso.sikoling.main.repository.alamat.PropinsiData;
import com.cso.sikoling.main.repository.person.JenisKelaminData;
import com.cso.sikoling.main.repository.person.PersonData;
import com.cso.sikoling.main.repository.perusahaan.DetailPelakuUsahaData;
import com.cso.sikoling.main.repository.perusahaan.KategoriModelPerizinanData;
import com.cso.sikoling.main.repository.perusahaan.KategoriPelakuUsahaData;
import com.cso.sikoling.main.repository.perusahaan.KategoriSkalaUsahaData;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
import com.cso.sikoling.main.repository.security.HakAksesData;
import com.cso.sikoling.main.repository.security.OtorisasiData;
import com.cso.sikoling.main.repository.security.oauth2.RealmData;
import com.cso.sikoling.main.util.GeneratorID;
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


public class RegisterDokumenRepositoryJPA implements Repository<RegisterDokumen, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public RegisterDokumenRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public RegisterDokumen save(RegisterDokumen t) throws SQLException {   
        try {
            RegisterDokumenData registerDokumenData = convertRegisterDokumenToRegisterDokumenData(t);
            entityManager.persist(registerDokumenData);
            entityManager.flush();             
            return convertRegisterDokumenDataToRegisterDokumen(registerDokumenData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data register dokumen");
        }        
    }

    @Override
    public RegisterDokumen update(RegisterDokumen t) throws SQLException {
        
        try {
            RegisterDokumenData registerDokumenData = 
                    convertRegisterDokumenToRegisterDokumenData(t);  
            registerDokumenData = entityManager.merge(registerDokumenData);
            return convertRegisterDokumenDataToRegisterDokumen(registerDokumenData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id regiter dokumen harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data register dokumen");
        }
        
    }

    @Override
    public RegisterDokumen updateId(String idLama, RegisterDokumen t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("RegisterDokumenData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id status dokumen");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id register dokumen");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            RegisterDokumenData registerDokumenData = entityManager.find(RegisterDokumenData.class, id);
            if(registerDokumenData != null) {
                entityManager.remove(registerDokumenData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("register dokumen dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id register dokumen harus bilangan 11 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<RegisterDokumen> getDaftarData(QueryParamFilters q) {
        
        List<RegisterDokumenData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<RegisterDokumenData> cq = cb.createQuery(RegisterDokumenData.class);
            Root<RegisterDokumenData> root = cq.from(RegisterDokumenData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "dokumen" -> daftarPredicate.add(cb.equal(root.get("dokumen").get("id"), filter.getValue()));
                        case "status_dokumen" -> daftarPredicate.add(cb.equal(root.get("statusDokumen").get("id"), filter.getValue()));
                        case "id_perusahaan" -> daftarPredicate.add(cb.equal(root.get("perusahaan").get("id"), filter.getValue()));
                        case "tanggal" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
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
                        case "validasi" -> daftarPredicate.add(cb.equal(root.get("isValidated"), filter.getValue()));
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
                        case "dokumen" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("dokumen").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("dokumen").get("nama")));
                            }
                        }
                        case "status_dokumen" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("statusDokumen").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("statusDokumen").get("nama")));
                            }
                        }
                        case "perusahaan" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("perusahaan").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("perusahaan").get("nama")));
                            }
                        }
                        case "validasi" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("isValidated")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("isValidated")));
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


            TypedQuery<RegisterDokumenData> typedQuery;	

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
                            .map(d -> convertRegisterDokumenDataToRegisterDokumen(d))
                            .collect(Collectors.toList());
            }
        }
        else {
            hasil = entityManager.createNamedQuery(
                    "RegisterDokumenData.findAll", 
                    RegisterDokumenData.class).getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                            .map(d -> convertRegisterDokumenDataToRegisterDokumen(d))
                            .collect(Collectors.toList());
            }
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<RegisterDokumenData> root = cq.from(RegisterDokumenData.class);		

        // where clause
        Iterator<Filter> iterFilter = f.iterator();
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();

        while (iterFilter.hasNext()) {
            Filter filter = (Filter) iterFilter.next();

            switch (filter.getField_name()) {
                case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                case "dokumen" -> daftarPredicate.add(cb.equal(root.get("dokumen").get("id"), filter.getValue()));
                case "status_dokumen" -> daftarPredicate.add(cb.equal(root.get("statusDokumen").get("id"), filter.getValue()));
                case "id_perusahaan" -> daftarPredicate.add(cb.equal(root.get("perusahaan").get("id"), filter.getValue()));
                case "tanggal" -> daftarPredicate.add(cb.equal(root.get("tanggalRegistrasi"), filter.getValue()));
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
                case "validasi" -> daftarPredicate.add(cb.equal(root.get("isValidated"), filter.getValue()));
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
    
    private RegisterDokumen convertRegisterDokumenDataToRegisterDokumen(RegisterDokumenData d) {
        RegisterDokumen registerDokumen = null;
		
        if(d != null) {
            PerusahaanData perusahaanData = d.getPerusahaan();
            KategoriModelPerizinanData kategoriModelPerizinanData = perusahaanData != null ?
                    perusahaanData.getModelPerizinan() : null;
            KategoriModelPerizinan kategoriModelPerizinan = kategoriModelPerizinanData != null ?
                    new KategoriModelPerizinan(
                        kategoriModelPerizinanData.getId(), 
                        kategoriModelPerizinanData.getNama(), 
                        kategoriModelPerizinanData.getSingkatan()
                    ) : null;
            KategoriSkalaUsahaData kategoriSkalaUsahaData = perusahaanData != null ?
                    perusahaanData.getSkalaUsaha() : null;
            KategoriSkalaUsaha kategoriSkalaUsaha = kategoriSkalaUsahaData != null ?
                    convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(kategoriSkalaUsahaData)
                    : null;            
            DetailPelakuUsahaData pelakuUsahaData = perusahaanData != null ?
                    perusahaanData.getPelakuUsaha() : null;
            KategoriPelakuUsahaData kategoriPelakuUsahaData = pelakuUsahaData != null ?
                    pelakuUsahaData.getKategoriPelakuUsaha() : null;            
            KategoriPelakuUsaha kategoriPelakuUsaha = kategoriPelakuUsahaData != null ?
                    new KategoriPelakuUsaha(
                        kategoriPelakuUsahaData.getId(), 
                        kategoriPelakuUsahaData.getNama(), 
                        kategoriSkalaUsaha
                    ) : null;            
            PelakuUsaha pelakuUsaha = pelakuUsahaData != null ?
                    new PelakuUsaha(
                        pelakuUsahaData.getId(), 
                        pelakuUsahaData.getNama(), 
                        pelakuUsahaData.getSingkatan(), 
                        kategoriPelakuUsaha
                    ) : null;
            PropinsiData propinsiData = perusahaanData != null ?
                    perusahaanData.getPropinsi() : null;
            Propinsi propinsi = propinsiData != null ?
                    new Propinsi(
                        propinsiData.getId(), 
                        propinsiData.getNama()
                    ) : null;
            KabupatenData kabupatenData = perusahaanData != null ?
                    perusahaanData.getKabupaten() : null;
            Kabupaten kabupaten = kabupatenData != null ?
                    new Kabupaten(
                        kabupatenData.getId(), 
                        kabupatenData.getNama(), 
                        propinsi != null ? propinsi.getId() : null
                    ) : null;            
            KecamatanData kecamatanData = perusahaanData != null ?
                    perusahaanData.getKecamatan() : null;
            Kecamatan kecamatan = kecamatanData != null ?
                    new Kecamatan(
                        kecamatanData.getId(), 
                        kecamatanData.getNama(), 
                        propinsi != null ? propinsi.getId() : null,
                        kabupaten != null ? kabupaten.getId() : null
                    ) : null;
            DesaData desaData = perusahaanData != null ?
                    perusahaanData.getDesa() : null;
            Desa desa = desaData != null ?
                    new Desa(
                        desaData.getId(), 
                        desaData.getNama(), 
                        propinsi != null ? propinsi.getId() : null,
                        kabupaten != null ? kabupaten.getId() : null,
                        kecamatan != null ? kecamatan.getId() : null
                    ) : null;
            Alamat alamat = new Alamat(
                propinsi, 
                kabupaten, 
                kecamatan, 
                desa, 
                perusahaanData != null ? perusahaanData.getDetailAlamat() : null
            );
            Kontak kontak = new Kontak(
                perusahaanData != null ? perusahaanData.getTelepone() : null, 
                perusahaanData != null ? perusahaanData.getFax() : null, 
                perusahaanData != null ? perusahaanData.getEmail() : null
            );
            Perusahaan perusahaan = perusahaanData != null ?
                    new Perusahaan(
                        perusahaanData.getId(), 
                        perusahaanData.getNpwp(), 
                        perusahaanData.getNama(), 
                        kategoriModelPerizinan, 
                        pelakuUsaha, 
                        alamat, 
                        kontak, 
                        perusahaanData.getTanggalRegistrasi()
                    ) : null;
            MasterDokumenData dokumenData = d.getDokumen();
            MasterDokumen dokumen = dokumenData != null ?
                    new MasterDokumen(
                            dokumenData.getId(), 
                            dokumenData.getNama(), 
                            dokumenData.getSingkatan(), 
                            dokumenData.getIdLama()
                    ): null;
            OtorisasiData otorisasiData = d.getUploader();
            HakAksesData hakAksesData = otorisasiData != null ?
                    otorisasiData.getHakAkses() : null;
            HakAkses hakAkses = hakAksesData != null ?
                    new HakAkses(
                            hakAksesData.getId(), 
                            hakAksesData.getNama(), 
                            hakAksesData.getKeterangan()
                    ) : null;
            PersonData personData = otorisasiData != null ?
                    otorisasiData.getPerson() : null;
            JenisKelaminData jenisKelaminData = personData != null ?
                    personData.getSex() : null;
            JenisKelamin jenisKelamin = jenisKelaminData != null ?
                    new JenisKelamin(
                            jenisKelaminData.getId(), 
                            jenisKelaminData.getNama()
                    ) : null;
            PropinsiData propinsiDataPerson = personData != null ?
                    personData.getPropinsi() : null;
            Propinsi propinsiPerson = propinsiDataPerson != null ?
                    new Propinsi(
                        propinsiDataPerson.getId(), 
                        propinsiDataPerson.getNama()
                    ) : null;
            KabupatenData kabupatenDataPerson = personData != null ? 
                    personData.getKabupaten() : null;
            Kabupaten kabupatenPerson = kabupatenDataPerson != null ?
                    new Kabupaten(
                        kabupatenDataPerson.getId(), 
                        kabupatenDataPerson.getNama(), 
                        propinsiPerson != null ? propinsiPerson.getId() : null
                    ) : null;            
            KecamatanData kecamatanDataPerson = personData != null ?
                    personData.getKecamatan() : null;
            Kecamatan kecamatanPerson = kecamatanDataPerson != null ?
                    new Kecamatan(
                        kecamatanDataPerson.getId(), 
                        kecamatanDataPerson.getNama(), 
                        propinsiPerson != null ? propinsiPerson.getId() : null,
                        kabupatenPerson != null ? kabupatenPerson.getId() : null
                    ) : null;
            DesaData desaDataPerson = personData != null ?
                    personData.getDesa() : null;
            Desa desaPerson = desaDataPerson != null ?
                    new Desa(
                        desaDataPerson.getId(), 
                        desaDataPerson.getNama(), 
                        propinsiPerson != null ? propinsiPerson.getId() : null,
                        kabupatenPerson != null ? kabupatenPerson.getId() : null,
                        kecamatanPerson != null ? kecamatanPerson.getId() : null
                    ) : null;
            Alamat alamatPerson = new Alamat(
                propinsiPerson, 
                kabupatenPerson, 
                kecamatanPerson, 
                desaPerson, 
                 personData != null ? personData.getDetailAlamat() : null
            );
            Kontak kontakPerson = new Kontak(
                personData != null ? personData.getTelepone() : null, 
                null,
                 personData != null ? personData.getEmail() : null
            );
            Person person = new Person(
                personData != null ? personData.getId() : null, 
                personData != null ? personData.getNama() : null, 
                jenisKelamin, 
                alamatPerson, 
                personData != null ? personData.getScanKtp() : null, 
                kontakPerson, 
                personData != null ? personData.getIsValidated() : null, 
                personData != null ? personData.getTanggalRegistrasi() : null
            );
            RealmData realmData = otorisasiData != null ?
                    otorisasiData.getRealm() : null;
            Realm realm = realmData != null ?
                    new Realm(realmData.getId(), realmData.getNama()) : null;            
            Otorisasi uploader = new Otorisasi(
                    otorisasiData != null ? otorisasiData.getId() : null, 
                    otorisasiData != null ? otorisasiData.getIdUser() : null, 
                    otorisasiData != null ? otorisasiData.getIsVerified() : null, 
                    otorisasiData != null ? otorisasiData.getUserName() : null, 
                    otorisasiData != null ? 
                            otorisasiData.getTanggalRegistrasi() : null, 
                    hakAkses, 
                    person, 
                    realm
                );
            StatusDokumenData statusDokumenData = d.getStatusDokumen();
            StatusDokumen statusDokumen = statusDokumenData != null ?
                    new StatusDokumen(
                            statusDokumenData.getId(), 
                            statusDokumenData.getNama()
                    ) : null;
            registerDokumen = new RegisterDokumen(
                    d.getId(), 
                    perusahaan, 
                    dokumen, 
                    d.getTanggalRegistrasi(), 
                    uploader, 
                    d.getNamaFile(), 
                    statusDokumen, 
                    d.getIdLama(), 
                    d.getIsValidated(),
                    d.getMetaFile(),
                    d.getMetaInfo()
            );
        }

        return registerDokumen;	
    }
    
    private RegisterDokumenData convertRegisterDokumenToRegisterDokumenData(RegisterDokumen t) {
        RegisterDokumenData registerDokumenData = null;
		
        if(t != null) {
            registerDokumenData = new RegisterDokumenData();            
            String idRegisterDokumen = t.getId();
            registerDokumenData.setId(idRegisterDokumen != null ? 
                    t.getId() : generateId(
                            t.getPerusahaan().getId(), t.getMasterDokumen().getId()));
            PerusahaanData perusahaanData = new PerusahaanData(t.getPerusahaan().getId());
            registerDokumenData.setPerusahaan(perusahaanData);
            MasterDokumenData dokumenData = new MasterDokumenData(t.getMasterDokumen().getId());
            registerDokumenData.setDokumen(dokumenData);
            registerDokumenData.setTanggalRegistrasi(t.getTanggalRegistrasi());
            OtorisasiData otorisasiData = new OtorisasiData(t.getUploader().getId());
            registerDokumenData.setUploader(otorisasiData);
            registerDokumenData.setNamaFile(t.getNamaFile());
            StatusDokumenData statusDokumenData = new StatusDokumenData(t.getStatusDokumen().getId());
            registerDokumenData.setStatusDokumen(statusDokumenData);
            registerDokumenData.setIdLama(t.getIdLama());
            registerDokumenData.setIsValidated(t.getIsValidated());
            registerDokumenData.setMetaFile(t.getMetaFile());
            registerDokumenData.setMetaInfo(t.getMetaInfo());
        }

        return registerDokumenData;
    }
    
    private String generateId(String idPerusahaan, String idDokumen) {
        int tahun = LocalDate.now().getYear();
        String hasil = idPerusahaan + idDokumen + Integer.toString(tahun);
        Query q = entityManager.createQuery("SELECT COUNT(rd.id) "
                        + "FROM RegisterDokumenData rd "
                        + "WHERE EXTRACT(YEAR FROM rd.tanggalRegistrasi) = :tahun"
                        + " AND rd.dokumen.id = :idDokumen AND rd.perusahaan.id = :idPerusahaan");

        q.setParameter("tahun", tahun);
        q.setParameter("idDokumen", idDokumen);
        q.setParameter("idPerusahaan", idPerusahaan);
        Long count = (Long) q.getSingleResult() + 1;
        hasil = hasil + GeneratorID.LPad(Long.toString(count), 2, '0');        
        return hasil;
    }
    
    private KategoriSkalaUsaha convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(KategoriSkalaUsahaData d) {
        KategoriSkalaUsaha kategoriSkalaUsaha = null;
		
        if(d != null) {
            kategoriSkalaUsaha = new KategoriSkalaUsaha(
                    d.getId(), d.getNama(), d.getSingkatan(), d.getKeterangan());
        }

        return kategoriSkalaUsaha;	
    }

}
