package com.cso.sikolingrestful.resources.alamat;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.Kabupaten;

public class KabupatenDTO {
    
    private String id;
    private String nama;

    public KabupatenDTO() {
    }

    public KabupatenDTO(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    
    public KabupatenDTO(Kabupaten t) {
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
    
    public Kabupaten toKabupaten() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json kabupaten tidak sesuai");
        }
        else {
            return new Kabupaten(this.id, this.nama);
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

        final KabupatenDTO other = (KabupatenDTO) obj;
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

        return "KabupatenDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
