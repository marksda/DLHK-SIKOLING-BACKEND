package com.cso.sikolingrestful.resources.dokumen;


import com.cso.sikoling.abstraction.entity.dokumen.StatusDokumen;
import java.util.Objects;

public class StatusDokumenDTO {

    private String id;
    private String nama;

    public StatusDokumenDTO() {
    }
    
    public StatusDokumenDTO(StatusDokumen t ) {
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
    
    public StatusDokumen toStatusDokumen() {
        return new StatusDokumen(this.id, this.nama);
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final StatusDokumenDTO other = (StatusDokumenDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "StatusDokumenDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
    
    
}
