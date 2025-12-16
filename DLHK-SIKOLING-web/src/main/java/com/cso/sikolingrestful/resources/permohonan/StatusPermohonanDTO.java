package com.cso.sikolingrestful.resources.permohonan;

import com.cso.sikoling.abstraction.entity.permohonan.StatusPermohonan;
import java.io.Serializable;
import java.util.Objects;


public class StatusPermohonanDTO implements Serializable {
    
    private String id;
    private String nama;

    public StatusPermohonanDTO() {
    }
    
    public StatusPermohonanDTO(StatusPermohonan t) {
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
    
    public StatusPermohonan toStatusPermohonan() {
        return new StatusPermohonan(this.id, this.nama);
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
        final StatusPermohonanDTO other = (StatusPermohonanDTO) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "StatusPermohonanDTO{id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }

}
