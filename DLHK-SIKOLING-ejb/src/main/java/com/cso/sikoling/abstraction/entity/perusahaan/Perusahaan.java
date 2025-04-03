package com.cso.sikoling.abstraction.entity.perusahaan;

import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import java.io.Serializable;
import java.util.Objects;

public class Perusahaan implements Serializable {
    
    private final String id;	
    private final String nama;
    private final KategoriModelPerizinan kategoriModelPerizinan;
    private final KategoriSkalaUsaha kategoriSkalaUsaha;	
    private final PelakuUsaha pelakuUsaha;
    private final Alamat alamat;
    private final Kontak kontak;

    public Perusahaan(String id, String nama, KategoriModelPerizinan kategoriModelPerizinan, 
        KategoriSkalaUsaha kategoriSkalaUsaha, PelakuUsaha pelakuUsaha, Alamat alamat, Kontak kontak) {
        
        this.id = id;
        this.nama = nama;
        this.kategoriModelPerizinan = kategoriModelPerizinan;
        this.kategoriSkalaUsaha = kategoriSkalaUsaha;
        this.pelakuUsaha = pelakuUsaha;
        this.alamat = alamat;
        this.kontak = kontak;
        
    }

    public KategoriModelPerizinan getKategoriModelPerizinan() {
        return kategoriModelPerizinan;
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

    public KategoriSkalaUsaha getKategoriSkalaUsaha() {
        return kategoriSkalaUsaha;
    }

    public PelakuUsaha getPelakuUsaha() {
        return pelakuUsaha;
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
                .concat(this.pelakuUsaha.getSingkatan())
                .concat(". ")
                .concat(this.nama)
                .concat("}");
        
    }	
	
}