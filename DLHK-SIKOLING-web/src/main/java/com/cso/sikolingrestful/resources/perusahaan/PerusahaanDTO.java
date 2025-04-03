package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikolingrestful.resources.alamat.AlamatDTO;
import com.cso.sikolingrestful.resources.alamat.KontakDTO;
import java.util.Objects;


public class PerusahaanDTO {
    
    private String id;	
    private String nama;
    private KategoriModelPerizinanDTO kategori_model_perizinan;
    private KategoriSkalaUsahaDTO kategori_skala_usaha;	
    private PelakuUsahaDTO pelaku_usaha;
    private AlamatDTO alamat;
    private KontakDTO kontak;

    public PerusahaanDTO() {
    }
    
    public PerusahaanDTO(Perusahaan t) {
        
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.kategori_model_perizinan = t.getKategori_model_erizinan() != null ?
                new KategoriModelPerizinanDTO(t.getKategori_model_erizinan()) : null;
            this.kategori_skala_usaha = t.getKategori_skala_usaha() != null ?
                    new KategoriSkalaUsahaDTO(t.getKategori_skala_usaha()) : null;
            this.pelaku_usaha = t.getPelaku_usaha() != null ? new PelakuUsahaDTO(t.getPelaku_usaha()) : null;
            this.alamat = t.getAlamat() != null ? new AlamatDTO(t.getAlamat()) : null;
            this.kontak = t.getKontak() != null ? new KontakDTO(t.getKontak()) : null;
        }
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public KategoriModelPerizinanDTO getKategori_model_perizinan() {
        return kategori_model_perizinan;
    }

    public void setKategori_model_perizinan(KategoriModelPerizinanDTO kategori_model_perizinan) {
        this.kategori_model_perizinan = kategori_model_perizinan;
    }

    public KategoriSkalaUsahaDTO getKategori_skala_usaha() {
        return kategori_skala_usaha;
    }

    public void setKategori_skala_usaha(KategoriSkalaUsahaDTO kategori_skala_usaha) {
        this.kategori_skala_usaha = kategori_skala_usaha;
    }

    public PelakuUsahaDTO getPelaku_usaha() {
        return pelaku_usaha;
    }

    public void setPelaku_usaha(PelakuUsahaDTO pelaku_usaha) {
        this.pelaku_usaha = pelaku_usaha;
    }

    public AlamatDTO getAlamat() {
        return alamat;
    }

    public void setAlamat(AlamatDTO alamat) {
        this.alamat = alamat;
    }

    public KontakDTO getKontak() {
        return kontak;
    }

    public void setKontak(KontakDTO kontak) {
        this.kontak = kontak;
    }
    
    public Perusahaan toPerusahaan() {
        if( this.id == null) {
            throw new IllegalArgumentException("format data json perusahaan tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            
            if(isDigit) {  
                return new Perusahaan(
                    this.id, 
                    this.nama, 
                    this.kategori_model_perizinan != null ? this.kategori_model_perizinan.toKategoriModelPerizinan() : null, 
                    this.kategori_skala_usaha != null ? this.kategori_skala_usaha.toKategoriSkalaUsaha() : null, 
                    this.pelaku_usaha != null ? this.pelaku_usaha.toPelakuUsaha() : null, 
                    this.alamat != null ? this.alamat.toAlamat() : null, 
                    this.kontak != null ? this.kontak.toKontak() : null
                );
            }
            else {
                throw new IllegalArgumentException("id pelaku usaha harus bilangan panjang 8 digit");
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        
        final PerusahaanDTO other = (PerusahaanDTO) obj;
        
        return Objects.equals(this.id, other.id);
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
