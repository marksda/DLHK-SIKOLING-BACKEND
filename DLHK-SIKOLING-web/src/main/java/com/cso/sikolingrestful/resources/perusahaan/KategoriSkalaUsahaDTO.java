package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import java.util.Objects;

public class KategoriSkalaUsahaDTO {
    
    private String id;	
    private String nama;
    private String singkatan;

    
    public KategoriSkalaUsahaDTO() {       
    }
    
    public KategoriSkalaUsahaDTO(String id, String nama, String singkatan) {
        this.id = id;
        this.nama = nama;
        this.singkatan = singkatan;
    }
    
    public KategoriSkalaUsahaDTO(KategoriSkalaUsaha t) {    
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.singkatan = t.getSingkatan();
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
    
    public KategoriSkalaUsaha toKategoriSkalaUsaha() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json jenis kelamin tidak sesuai");
        }
        else {
            return new KategoriSkalaUsaha(this.id, this.nama, this.singkatan);
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
