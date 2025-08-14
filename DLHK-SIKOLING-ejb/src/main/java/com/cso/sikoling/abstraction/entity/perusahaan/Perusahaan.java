package com.cso.sikoling.abstraction.entity.perusahaan;

import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Perusahaan implements Serializable {
    
    private final String id;	
    private final String npwp;	
    private final String nama;
    private final KategoriModelPerizinan kategori_model_perizinan;
//    private final KategoriSkalaUsaha kategori_skala_usaha;	
    private final PelakuUsaha pelaku_usaha;
    private final Alamat alamat;
    private final Kontak kontak;
    private final Date tanggal_registrasi;

//    public Perusahaan(String id, String npwp, String nama, KategoriModelPerizinan kategori_model_erizinan, 
//        KategoriSkalaUsaha kategori_skala_usaha, PelakuUsaha pelaku_usaha, Alamat alamat, Kontak kontak) {
    public Perusahaan(String id, String npwp, String nama, KategoriModelPerizinan kategori_model_perizinan,
            PelakuUsaha pelaku_usaha, Alamat alamat, Kontak kontak, Date tanggal_registrasi) {
        
        this.id = id;
        this.npwp = npwp;
        this.nama = nama;
        this.kategori_model_perizinan = kategori_model_perizinan;
//        this.kategori_skala_usaha = kategori_skala_usaha;
        this.pelaku_usaha = pelaku_usaha;
        this.alamat = alamat;
        this.kontak = kontak;
        this.tanggal_registrasi = tanggal_registrasi;
        
    }

    public String getId() {
        return id;
    }

    public String getNpwp() {
        return npwp;
    }
	
    public String getNama() {
        return nama;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public Kontak getKontak() {
        return kontak;
    }

//    public KategoriSkalaUsaha getKategori_skala_usaha() {
//        return kategori_skala_usaha;
//    }

    public KategoriModelPerizinan getKategori_model_perizinan() {
        return kategori_model_perizinan;
    }
    
    public PelakuUsaha getPelaku_usaha() {
        return pelaku_usaha;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }
	
    @Override
    public int hashCode() {

        int hash = 91;
        hash = 11 * hash + Objects.hashCode(this.id);

        return hash;

    }
	
    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Perusahaan other = (Perusahaan) obj;

        return this.id.equals(other.getId());
        
    }
	
    @Override
    public String toString() {
        
        return "Perusahaan{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.pelaku_usaha.getSingkatan())
                .concat(". ")
                .concat(this.nama)
                .concat("}");
        
    }	
	
}