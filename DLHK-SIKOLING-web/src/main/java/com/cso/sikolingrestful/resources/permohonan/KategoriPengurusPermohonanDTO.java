package com.cso.sikolingrestful.resources.permohonan;

import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
import java.io.Serializable;
import java.util.Objects;


public class KategoriPengurusPermohonanDTO implements Serializable {
    
    private String id;
    private String nama;

    public KategoriPengurusPermohonanDTO() {
    }
    
    public KategoriPengurusPermohonanDTO(KategoriPengurusPermohonan t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
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
    
    public KategoriPengurusPermohonan toKategoriPengurusPermohonan() {
        return new KategoriPengurusPermohonan(this.id, this.nama);
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
        final KategoriPengurusPermohonanDTO other = (KategoriPengurusPermohonanDTO) obj;
        
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
