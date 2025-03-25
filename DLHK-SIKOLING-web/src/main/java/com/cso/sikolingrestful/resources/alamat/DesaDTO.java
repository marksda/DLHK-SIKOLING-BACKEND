package com.cso.sikolingrestful.resources.alamat;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.Desa;

public class DesaDTO {
    
    private String id;
    private String nama;

    public DesaDTO() {
    }

    public DesaDTO(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    
    public DesaDTO(Desa t) {
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
    
    public Desa toDesa() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json desa tidak sesuai");
        }
        else {
            return new Desa(this.id, this.nama);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 753;
        hash = 73 * hash + Objects.hashCode(id );
        hash = 73 * hash + Objects.hashCode(nama);
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

        final DesaDTO other = (DesaDTO) obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return !(this.nama == null ? other.nama != null : !this.nama.equals(other.nama));
    }

    @Override
    public String toString() {
        if(id == null) {
            return null;
        }			

        return "DesaDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
