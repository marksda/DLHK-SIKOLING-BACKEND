package com.cso.sikolingrestful.resources.perusahaan;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;

public class PelakuUsahaDTO {
    
    private String id;
    private String nama;
    private String singkatan;
    private KategoriPelakuUsahaDTO kategori_pelaku_usaha;

    public PelakuUsahaDTO() {
    }

    public PelakuUsahaDTO(PelakuUsaha t) {
        this.id = t.getId();
        this.nama = t.getNama();
        this.singkatan = t.getSingkatan();
        this.kategori_pelaku_usaha = t.getKategori_pelaku_usaha() != null ? 
                new KategoriPelakuUsahaDTO(t.getKategori_pelaku_usaha()) : null;
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

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public KategoriPelakuUsahaDTO getKategori_pelaku_usaha() {
        return kategori_pelaku_usaha;
    }

    public void setKategori_pelaku_usaha(KategoriPelakuUsahaDTO kategori_pelaku_usaha) {
        this.kategori_pelaku_usaha = kategori_pelaku_usaha;
    }

    public PelakuUsaha toPelakuUsaha() {
        
        if( this.id == null) {
            throw new IllegalArgumentException("format data json pelaku usaha tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            
            if(isDigit) {  
                return new PelakuUsaha(
                    id, nama, singkatan, 
                    kategori_pelaku_usaha != null ? kategori_pelaku_usaha.toKategoriPelakuUsaha():null
                );
            }
            else {
                throw new IllegalArgumentException("id pelaku usaha harus bilangan panjang 6 digit");
            }
        }
        
    }

    @Override
    public int hashCode() {
        
        int hash = 131;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.nama);
        
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

        final PelakuUsahaDTO other = (PelakuUsahaDTO) obj;
        
        if (this.id.equals(other.getId())) {
            return false;
        }
        
        return !this.nama.equals(other.getNama());
        
    }

    @Override
    public String toString() {
        return "DetailPelakuUsahaDTO{"
                .concat("id=")
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");	    
    }
	
}
