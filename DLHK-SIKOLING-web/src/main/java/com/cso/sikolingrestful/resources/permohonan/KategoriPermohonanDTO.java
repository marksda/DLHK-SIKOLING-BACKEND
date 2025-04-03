package com.cso.sikolingrestful.resources.permohonan;

import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import java.io.Serializable;
import java.util.Objects;


public class KategoriPermohonanDTO implements Serializable {
    
    private String id;
    private String nama;
    private String id_lama;

    public KategoriPermohonanDTO() {
    }
    
    public KategoriPermohonanDTO(KategoriPermohonan t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.id_lama = t.getId_lama();
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

    public String getId_lama() {
        return id_lama;
    }

    public void setId_lama(String id_lama) {
        this.id_lama = id_lama;
    }
    
    public KategoriPermohonan toKategoriPermohonan() {
        return new KategoriPermohonan(this.id, this.nama, this.id_lama);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final KategoriPermohonanDTO other = (KategoriPermohonanDTO) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "KategoriPermohonanDTO{id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }

}
