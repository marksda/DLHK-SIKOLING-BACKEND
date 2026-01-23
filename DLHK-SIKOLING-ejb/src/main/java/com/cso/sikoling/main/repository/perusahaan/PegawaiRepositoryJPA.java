package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
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
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.repository.alamat.DesaData;
import com.cso.sikoling.main.repository.alamat.KabupatenData;
import com.cso.sikoling.main.repository.alamat.KecamatanData;
import com.cso.sikoling.main.repository.alamat.PropinsiData;
import com.cso.sikoling.main.repository.person.JenisKelaminData;
import com.cso.sikoling.main.repository.person.PersonData;
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


public class PegawaiRepositoryJPA implements Repository<Pegawai, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PegawaiRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Pegawai save(Pegawai t) throws SQLException {   
        try {
            PegawaiPerusahaanData pegawaiData = convertPegawaiToPegawaiPerusahaanData(t);
            entityManager.persist(pegawaiData);
            entityManager.flush();  
            
            return convertPegawaiPerusahaanDataToPegawai(pegawaiData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pegawai harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data pegawai");
        }        
    }

    @Override
    public Pegawai update(Pegawai t) throws SQLException {
        
        try {
            PegawaiPerusahaanData pegawaiData = convertPegawaiToPegawaiPerusahaanData(t);  
            pegawaiData = entityManager.merge(pegawaiData);
            return convertPegawaiPerusahaanDataToPegawai(pegawaiData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pegawai harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data pegawai");
        }
        
    }

    @Override
    public Pegawai updateId(String idLama, Pegawai t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PegawaiPerusahaanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id pegawai");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pegawai harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id pegawai");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            PegawaiPerusahaanData pegawaiData = entityManager.find(PegawaiPerusahaanData.class, id);
            if(pegawaiData != null) {
                entityManager.remove(pegawaiData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("pegawai dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id pegawai harus bilangan");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Pegawai> getDaftarData(QueryParamFilters q) {
        
        List<PegawaiPerusahaanData> hasil;
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PegawaiPerusahaanData> cq = cb.createQuery(PegawaiPerusahaanData.class);
            Root<PegawaiPerusahaanData> root = cq.from(PegawaiPerusahaanData.class);		

            // where clause
            if(q.getDaftarFieldFilter() != null) {
                Iterator<Filter> iterFilter = q.getDaftarFieldFilter().iterator();
                ArrayList<Predicate> daftarPredicate = new ArrayList<>();
                while (iterFilter.hasNext()) {
                    Filter filter = (Filter) iterFilter.next();

                    switch (filter.getField_name()) {
                        case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                        case "id_person" -> daftarPredicate.add(cb.equal(root.get("person").get("id"), filter.getValue()));
                        case "id_perusahaan" -> daftarPredicate.add(cb.equal(root.get("perusahaan").get("id"), filter.getValue()));
                        case "person" -> daftarPredicate.add(cb.like(cb.lower(root.get("person").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "jabatan" -> daftarPredicate.add(cb.like(cb.lower(root.get("jabatan").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                        case "perusahaan" -> daftarPredicate.add(cb.like(cb.lower(root.get("perusahaan").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
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
                        case "person" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("person").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("person").get("nama")));
                            }
                        }
                        case "jabatan" -> {
                            if(sort.getValue().equals("asc")) {
                                cq.orderBy(cb.asc(root.get("jabatan").get("nama")));
                            }
                            else {
                                cq.orderBy(cb.desc(root.get("jabatan").get("nama")));
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
                        default -> {
                        }
                    }			
                }
            }


            TypedQuery<PegawaiPerusahaanData> typedQuery;	

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
                            .map(d -> convertPegawaiPerusahaanDataToPegawai(d))
                            .collect(Collectors.toList());
            }
        }
        else {
            hasil = entityManager.createNamedQuery(
                    "PegawaiPerusahaanData.findAll", 
                    PegawaiPerusahaanData.class).getResultList();
            
            if(hasil.isEmpty()) {
                return null;
            }
            else {
                return hasil.stream()
                            .map(d -> convertPegawaiPerusahaanDataToPegawai(d))
                            .collect(Collectors.toList());
            }
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PegawaiPerusahaanData> root = cq.from(PegawaiPerusahaanData.class);		

        // where clause
        ArrayList<Predicate> daftarPredicate = new ArrayList<>();
        if( f != null) {
            Iterator<Filter> iterFilter = f.iterator();

            while (iterFilter.hasNext()) {
                Filter filter = (Filter) iterFilter.next();

                switch (filter.getField_name()) {
                    case "id" -> daftarPredicate.add(cb.equal(root.get("id"), filter.getValue()));
                    case "id_person" -> daftarPredicate.add(cb.equal(root.get("person").get("id"), filter.getValue()));
                    case "id_perusahaan" -> daftarPredicate.add(cb.equal(root.get("perusahaan").get("id"), filter.getValue()));
                    case "person" -> daftarPredicate.add(cb.like(cb.lower(root.get("person").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    case "jabatan" -> daftarPredicate.add(cb.like(cb.lower(root.get("jabatan").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
                    case "perusahaan" -> daftarPredicate.add(cb.like(cb.lower(root.get("perusahaan").get("nama")), "%"+filter.getValue().toLowerCase()+"%"));
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
    
    private Pegawai convertPegawaiPerusahaanDataToPegawai(PegawaiPerusahaanData d) {
        Pegawai pegawai = null;
		
        if(d != null) {   
            
            PerusahaanData perusahaanData = d.getPerusahaan();
            Perusahaan perusahaan = null;

            if(perusahaanData != null) {
                KategoriModelPerizinanData kategoriModelPerizinanData = perusahaanData.getModelPerizinan();            
                KategoriModelPerizinan kategoriModelPerizinan = kategoriModelPerizinanData != null ?
                        new KategoriModelPerizinan(
                            kategoriModelPerizinanData.getId(), 
                            kategoriModelPerizinanData.getNama(), 
                            kategoriModelPerizinanData.getSingkatan()
                        ) 
                        : null;
                
                KategoriSkalaUsahaData kategoriSkalaUsahaData = perusahaanData.getSkalaUsaha();
                KategoriSkalaUsaha kategoriSkalaUsaha = kategoriSkalaUsahaData != null ?
                        convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(kategoriSkalaUsahaData)
                        : null;

                DetailPelakuUsahaData pelakuUsahaData = perusahaanData.getPelakuUsaha();
                KategoriPelakuUsahaData kategoriPelakuUsahaData = pelakuUsahaData != null ?
                        pelakuUsahaData.getKategoriPelakuUsaha() : null;
                KategoriPelakuUsaha kategoriPelakuUsaha = kategoriPelakuUsahaData != null ?
                        new KategoriPelakuUsaha(
                            kategoriPelakuUsahaData.getId(), 
                            kategoriPelakuUsahaData.getNama(), 
                            kategoriSkalaUsaha
                        ) 
                        : null;
                PelakuUsaha pelakuUsaha = pelakuUsahaData != null ?
                    new PelakuUsaha(
                        pelakuUsahaData.getId(), 
                        pelakuUsahaData.getNama(), 
                        pelakuUsahaData.getSingkatan(), 
                        kategoriPelakuUsaha
                    )
                    : null;
            
                PropinsiData propinsiData = perusahaanData.getPropinsi();
                Propinsi propinsi = propinsiData != null ?
                        new Propinsi(
                            propinsiData.getId(), 
                            propinsiData.getNama()
                        )
                        : null;

                KabupatenData kabupatenData = perusahaanData.getKabupaten();
                Kabupaten kabupaten = kabupatenData != null ?
                        new Kabupaten(
                            kabupatenData.getId(), 
                            kabupatenData.getNama(), 
                            propinsi != null ? propinsi.getId() : null
                        )
                        : null;

                KecamatanData kecamatanData = perusahaanData.getKecamatan();
                Kecamatan kecamatan = kecamatanData != null ?
                        new Kecamatan(
                            kecamatanData.getId(), 
                            kecamatanData.getNama(), 
                            propinsi != null ? propinsi.getId() : null,
                            kabupaten != null ? kabupaten.getId() : null
                        )
                        : null;

                DesaData desaData = perusahaanData.getDesa();
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
                    perusahaanData.getDetailAlamat()
                );

                Kontak kontak = new Kontak(
                    perusahaanData.getTelepone(), 
                    perusahaanData.getFax(), 
                    perusahaanData.getEmail()
                );

                perusahaan = new Perusahaan(
                    perusahaanData.getId(), 
                    perusahaanData.getNpwp(),
                    perusahaanData.getNama(), 
                    kategoriModelPerizinan, 
                    pelakuUsaha, 
                    alamat, 
                    kontak,
                    perusahaanData.getTanggalRegistrasi()
                );
            }

            PersonData personData = d.getPerson() != null ? d.getPerson() : null;
            Person person = null;
                
            if(personData != null) {
                PropinsiData propinsiDataPerson =  personData.getPropinsi();
                Propinsi propinsiPerson = propinsiDataPerson != null ? 
                        new Propinsi(
                            propinsiDataPerson.getId(), 
                            propinsiDataPerson.getNama()
                        ) : null;

                KabupatenData kabupatenDataPerson = personData.getKabupaten();
                Kabupaten kabupatenPerson = kabupatenDataPerson != null ?
                    new Kabupaten(
                        kabupatenDataPerson.getId(), 
                        kabupatenDataPerson.getNama(), 
                        propinsiPerson != null ? propinsiPerson.getId() : null
                    ) : null;

                KecamatanData kecamatanDataPerson = personData.getKecamatan();
                Kecamatan kecamatanPerson = kecamatanDataPerson != null ? 
                        new Kecamatan(
                            kecamatanDataPerson.getId(), 
                            kecamatanDataPerson.getNama(), 
                            propinsiPerson != null ? propinsiPerson.getId() : null,
                            kabupatenPerson != null ? kabupatenPerson.getId() : null
                        ) : null;

                DesaData desaDataPerson = personData.getDesa();
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
                        personData.getDetailAlamat()
                );    

                Kontak kontakPerson = new Kontak(personData.getTelepone(), null, personData.getEmail());
                JenisKelaminData jenisKelaminData = personData.getSex();
                JenisKelamin jenisKelamin = jenisKelaminData != null ?
                        new JenisKelamin(jenisKelaminData.getId(), jenisKelaminData.getNama()) : null;
                person = new Person(
                        personData.getId(), 
                        personData.getNama(), 
                        jenisKelamin, 
                        alamatPerson, 
                        personData.getScanKtp(), 
                        kontakPerson, 
                        personData.getIsValidated(),
                        personData.getTanggalRegistrasi()
                );

            }
            
            JabatanData jabatanData = d.getJabatan();
            Jabatan jabatan = null;
            
            if(jabatanData != null) {
                jabatan = new Jabatan(jabatanData.getId(), jabatanData.getNama());
            }

            pegawai = new Pegawai(
                    d.getId(), 
                    perusahaan, 
                    person, 
                    jabatan,
                    d.getStatusAktif()
            );    
            
        }

        return pegawai;	
    }
    
    private PegawaiPerusahaanData convertPegawaiToPegawaiPerusahaanData(Pegawai t) {
        PegawaiPerusahaanData pegawaiData = null;
		
        if(t != null) {
            pegawaiData = new PegawaiPerusahaanData();
            String id = t.getId();
            pegawaiData.setId(id != null ? id : t.getPerson().getId().concat(t.getPerusahaan().getId()));
            PersonData personData = t.getPerson() != null ? 
                    new PersonData(t.getPerson().getId()) : null;            
            pegawaiData.setPerson(personData);
            PerusahaanData perusahaanData = t.getPerusahaan() != null ? 
                    new PerusahaanData(t.getPerusahaan().getId()) : null;
            pegawaiData.setPerusahaan(perusahaanData);
            JabatanData jabatanData = t.getJabatan() != null ? 
                    new JabatanData(t.getJabatan().getId()) : null;
            pegawaiData.setJabatan(jabatanData);
            pegawaiData.setStatusAktif(t.getStatusAktif());
        }

        return pegawaiData;
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
