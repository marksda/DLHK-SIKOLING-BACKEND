package com.cso.sikolingrestful.resources.perusahaan;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;

public class KategoriModelPerizinanDTO {
    
    private String id;
    private String nama;
    private String singkatan;

    public KategoriModelPerizinanDTO() {
    }

    public KategoriModelPerizinanDTO(KategoriModelPerizinan t) {
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
	
    public KategoriModelPerizinan toKategoriModelPerizinan() {
        return new KategoriModelPerizinan(id, nama, singkatan);
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        
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

        final KategoriModelPerizinanDTO other = (KategoriModelPerizinanDTO) obj;
        
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "ModelPerizinanDTO{"
                .concat("id=")
                .concat(id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
	
}
