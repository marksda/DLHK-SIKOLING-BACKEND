package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Paging;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.SortOrder;
import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
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


public class PerusahaanRepositoryJPA implements Repository<Perusahaan, QueryParamFilters, Filter> {
    
    private final EntityManager entityManager;

    public PerusahaanRepositoryJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Perusahaan save(Perusahaan t) throws SQLException {   
        try {
            PerusahaanData perusahaanData = convertPerusahaanToPerusahaanData(t);
            entityManager.persist(perusahaanData);
            entityManager.flush();  
            
            return convertPerusahaanDataToPerusahaan(perusahaanData);  
        } 
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id perusahaan harus bilangan dan panjang 8 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data perusahaan");
        }        
    }

    @Override
    public Perusahaan update(Perusahaan t) throws SQLException {
        
        try {
            PerusahaanData perusahaanData = convertPerusahaanToPerusahaanData(t);  
            perusahaanData = entityManager.merge(perusahaanData);
            return convertPerusahaanDataToPerusahaan(perusahaanData);   
        }         
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id perusahaan harus bilangan dan panjang 8 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Duplikasi data perusahaan");
        }
        
    }

    @Override
    public Perusahaan updateId(String idLama, Perusahaan t) throws SQLException {
        
        Query query = entityManager.createNamedQuery("PerusahaanData.updateId");
        query.setParameter("idBaru", t.getId());
        query.setParameter("idLama", idLama);
        try {
            int updateCount = query.executeUpdate();
            if(updateCount > 0) {
                return update(t);
            }
            else {
                throw new SQLException("Gagal mengupdate id perusahaan");
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id perusahaan harus bilangan dan panjang 8 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException("Dulpikasi id perusahaan");
        }
        
    }

    @Override
    public boolean delete(String id) throws SQLException {
        
        try {
            PerusahaanData perusahaanData = entityManager.find(PerusahaanData.class, id);
            if(perusahaanData != null) {
                entityManager.remove(perusahaanData);	
                entityManager.flush();
                return true;
            }
            else {
                throw new SQLException("perusahaan dengan id:".concat(id).concat(" tidak ditemukan"));
            }
        }
        catch(ConstraintViolationException cstVltException) {
            throw new SQLException("id perusahaan harus bilangan dan panjang 8 digit");
        }
        catch (PersistenceException e) {
            throw new SQLException(e.getLocalizedMessage());
        }
        
    }

    @Override
    public List<Perusahaan> getDaftarData(QueryParamFilters q) {
        
        if(q != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PerusahaanData> cq = cb.createQuery(PerusahaanData.class);
            Root<PerusahaanData> root = cq.from(PerusahaanData.class);		

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


            TypedQuery<PerusahaanData> typedQuery;	

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
                            .map(d -> convertPerusahaanDataToPerusahaan(d))
                            .collect(Collectors.toList());
        }
        else {
            return entityManager.createNamedQuery("PerusahaanData.findAll", PerusahaanData.class)
                 .getResultList()
                 .stream()
                 .map(d -> convertPerusahaanDataToPerusahaan(d))
                            .collect(Collectors.toList());
        }
        
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PerusahaanData> root = cq.from(PerusahaanData.class);		

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
    
    private Perusahaan convertPerusahaanDataToPerusahaan(PerusahaanData d) {
        Perusahaan perusahaan = null;
		
        if(d != null) {
            KategoriModelPerizinanData kategoriModelPerizinanData = d.getModelPerizinan();
            KategoriModelPerizinan kategoriModelPerizinan = kategoriModelPerizinanData != null ?
                    new KategoriModelPerizinan(
                        kategoriModelPerizinanData.getId(), 
                        kategoriModelPerizinanData.getNama(), 
                        kategoriModelPerizinanData.getSingkatan()
                    ) 
                    : null;
            
            KategoriSkalaUsahaData kategoriSkalaUsahaData = d.getSkalaUsaha();
            KategoriSkalaUsaha kategoriSkalaUsaha = kategoriSkalaUsahaData != null ?
                    convertKategoriSkalaUsahaDataToKategoriSkalaUsaha(kategoriSkalaUsahaData)
                    : null;
            
            DetailPelakuUsahaData pelakuUsahaData = d.getPelakuUsaha();
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
            
            PropinsiData propinsiData = d.getPropinsi();
            Propinsi propinsi = propinsiData != null ?
                    new Propinsi(
                        propinsiData.getId(), 
                        propinsiData.getNama()
                    )
                    : null;
            
            KabupatenData kabupatenData = d.getKabupaten();
            Kabupaten kabupaten = kabupatenData != null ?
                    new Kabupaten(
                        kabupatenData.getId(), 
                        kabupatenData.getNama(), 
                        kabupatenData.getPropinsi() != null ?
                                kabupatenData.getPropinsi().getId() : null
                    )
                    : null;
            
            KecamatanData kecamatanData = d.getKecamatan();
            Kecamatan kecamatan = kecamatanData != null ?
                    new Kecamatan(
                        kecamatanData.getId(), 
                        kecamatanData.getNama(), 
                        kecamatanData.getKabupaten() != null ?
                                kecamatanData.getKabupaten().getId() : null
                    )
                    : null;
            
            DesaData desaData = d.getDesa();
            Desa desa = desaData != null ?
                    new Desa(
                        desaData.getId(), 
                        desaData.getNama(), 
                        desaData.getKecamatan() != null ?
                                desaData.getKecamatan().getId() : null
                    )
                    : null;
                    
            Alamat alamat = new Alamat(
                propinsi, 
                kabupaten, 
                kecamatan, 
                desa, 
                d.getDetailAlamat()
            );
            
            Kontak kontak = new Kontak(
                d.getTelepone(), 
                d.getFax(), 
                d.getEmail()
            );
            
            perusahaan = new Perusahaan(
                d.getId(), 
                d.getNpwp(),
                d.getNama(), 
                kategoriModelPerizinan,
                pelakuUsaha,   
                alamat,
                kontak    
            );
        }

        return perusahaan;	
    }
    
    private PerusahaanData convertPerusahaanToPerusahaanData(Perusahaan t) {
        PerusahaanData perusahaanData = null;
		
        if(t != null) {
            perusahaanData = new PerusahaanData();
            perusahaanData.setId(t.getId());
            perusahaanData.setNpwp(t.getNpwp());
            perusahaanData.setNama(t.getNama());
            PropinsiData propinsiData = 
                    new PropinsiData(t.getAlamat().getPropinsi().getId());
            perusahaanData.setPropinsi(propinsiData);
            KabupatenData kabupatenData = 
                    new KabupatenData(t.getAlamat().getKabupaten().getId());
            perusahaanData.setKabupaten(kabupatenData);
            KecamatanData kecamatanData = 
                    new KecamatanData(t.getAlamat().getKecamatan().getId());
            perusahaanData.setKecamatan(kecamatanData);
            DesaData desaData = new DesaData(t.getAlamat().getDesa().getId());
            perusahaanData.setDesa(desaData);
            perusahaanData.setDetailAlamat(t.getAlamat().getKeterangan());
            KategoriModelPerizinanData kategoriModelPerizinanData = 
                    new KategoriModelPerizinanData(
                        t.getKategori_model_perizinan().getId()
                    );
            perusahaanData.setModelPerizinan(kategoriModelPerizinanData);
            KategoriSkalaUsahaData kategoriSkalaUsahaData = 
                    new KategoriSkalaUsahaData(
                        t.getPelaku_usaha()
                        .getKategori_pelaku_usaha()
                        .getKategori_skala_usaha().getId()
                    );
            perusahaanData.setSkalaUsaha(kategoriSkalaUsahaData);
            DetailPelakuUsahaData pelakuUsahaData = 
                    new DetailPelakuUsahaData(t.getPelaku_usaha().getId());
            perusahaanData.setPelakuUsaha(pelakuUsahaData);            
            perusahaanData.setStatusVerifikasi(false);
            perusahaanData.setTelepone(t.getKontak().getTelepone());
            perusahaanData.setFax(t.getKontak().getFax());
            perusahaanData.setEmail(t.getKontak().getEmail());            
        }

        return perusahaanData;
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
