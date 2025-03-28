package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import java.util.Objects;

public class KategoriPelakuUsahaDTO {
    private String id;
    private String nama;
    private String id_kategori_skala_usaha;

    public KategoriPelakuUsahaDTO() {
    }

    public KategoriPelakuUsahaDTO(String id, String nama, String id_kategori_skala_usaha) {
        this.id = id;
        this.nama = nama;
        this.id_kategori_skala_usaha = id_kategori_skala_usaha;
    }
    
    public KategoriPelakuUsahaDTO(KategoriPelakuUsaha t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.id_kategori_skala_usaha = t.getId_kategori_skala_usaha();
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

    public String getId_kategori_skala_usaha() {
        return id_kategori_skala_usaha;
    }

    public void setId_kategori_skala_usaha(String id_kategori_skala_usaha) {
        this.id_kategori_skala_usaha = id_kategori_skala_usaha;
    }
    
    public KategoriPelakuUsaha toKategoriPelakuUsaha() {
        if( this.id == null ) {
            throw new IllegalArgumentException("format data json kategori pelaku usaha tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            
            if(isDigit) {            
                return new KategoriPelakuUsaha(this.id, this.nama, this.id_kategori_skala_usaha);
            }
            else {
                throw new IllegalArgumentException("id kategori pelaku usaha harus bilangan panjang 2 digit");
            }
        }
    }
    
    @Override
    public int hashCode() {

        int hash = 71;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nama);
        
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

        final KategoriPelakuUsahaDTO other = (KategoriPelakuUsahaDTO) obj;

        return this.id.equals(other.id);
        
    }

    @Override
    public String toString() {
        return "JenisPelakuUsaha{"
                .concat("id=")
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }
    
}
