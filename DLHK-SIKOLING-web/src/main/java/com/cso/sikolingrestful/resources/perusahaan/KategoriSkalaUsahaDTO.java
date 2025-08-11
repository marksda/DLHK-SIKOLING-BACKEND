package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import java.util.Objects;

public class KategoriSkalaUsahaDTO {

    private String id;	
    private String nama;
    private String singkatan;
    private String keterangan;
    
    public KategoriSkalaUsahaDTO() {       
    }
    
    public KategoriSkalaUsahaDTO(String id, String nama, String singkatan, String keterangan) {
        this.id = id;
        this.nama = nama;
        this.singkatan = singkatan;
        this.keterangan = keterangan;
    }
    
    public KategoriSkalaUsahaDTO(KategoriSkalaUsaha t) {    
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.singkatan = t.getSingkatan();
            this.keterangan = t.getKeterangan();
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

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public KategoriSkalaUsaha toKategoriSkalaUsaha() {
        
        if( this.id == null ) {
            throw new IllegalArgumentException("format data json kategori skala usaha tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            
            if(isDigit) {            
                return new KategoriSkalaUsaha(
                        this.id, this.nama, this.singkatan, this.keterangan);
            }
            else {
                throw new IllegalArgumentException("id kategori skala usaha harus bilangan panjang 2 digit");
            }
        }
        
    }
    
    @Override
    public int hashCode() {

        int hash = 43;
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

        final KategoriSkalaUsahaDTO other = (KategoriSkalaUsahaDTO) obj;

        return this.id.equals(other.getId());

    }
	
    @Override
    public String toString() {
        return "SkalaUsaha { id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat(", singkatan=")
                .concat(this.singkatan)
                .concat("}");
    }
    
}
