package com.cso.sikoling.abstraction.entity.perusahaan;

import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import java.io.Serializable;
import java.util.Objects;

public class Perusahaan implements Serializable {
    
    private final String id;	
    private final String nama;
    private final KategoriModelPerizinan kategori_model_erizinan;
    private final KategoriSkalaUsaha kategori_skala_usaha;	
    private final PelakuUsaha pelaku_usaha;
    private final Alamat alamat;
    private final Kontak kontak;

    public Perusahaan(String id, String nama, KategoriModelPerizinan kategori_model_erizinan, 
        KategoriSkalaUsaha kategori_skala_usaha, PelakuUsaha pelaku_usaha, Alamat alamat, Kontak kontak) {
        
        this.id = id;
        this.nama = nama;
        this.kategori_model_erizinan = kategori_model_erizinan;
        this.kategori_skala_usaha = kategori_skala_usaha;
        this.pelaku_usaha = pelaku_usaha;
        this.alamat = alamat;
        this.kontak = kontak;
        
    }

    public KategoriModelPerizinan getKategori_model_erizinan() {
        return kategori_model_erizinan;
    }

    public String getId() {
        return id;
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

    public KategoriSkalaUsaha getKategori_skala_usaha() {
        return kategori_skala_usaha;
    }

    public PelakuUsaha getPelaku_usaha() {
        return pelaku_usaha;
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