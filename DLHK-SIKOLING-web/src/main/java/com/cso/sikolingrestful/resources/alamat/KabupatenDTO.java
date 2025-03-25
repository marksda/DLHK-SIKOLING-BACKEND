package com.cso.sikolingrestful.resources.alamat;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.Kabupaten;

public class KabupatenDTO {
    
    private String id;
    private String nama;
    private String id_propinsi;

    public KabupatenDTO() {
    }

    public KabupatenDTO(String id, String nama, String id_propinsi) {
        this.id = id;
        this.nama = nama;
        this.id_propinsi = id_propinsi;
    }
    
    public KabupatenDTO(Kabupaten t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.id_propinsi = t.getId_propinsi();
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

    public String getId_propinsi() {
        return id_propinsi;
    }

    public void setId_propinsi(String id_propinsi) {
        this.id_propinsi = id_propinsi;
    }
    
    public Kabupaten toKabupaten() {
        if( this.id == null || this.nama == null || this.id_propinsi == null) {
            throw new IllegalArgumentException("format data json kabupaten tidak sesuai");
        }
        else {
            return new Kabupaten(this.id, this.nama, this.id_propinsi);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 753;
        hash = 73 * hash + Objects.hashCode(id );
        hash = 73 * hash + Objects.hashCode(nama);
        hash = 73 * hash + Objects.hashCode(id_propinsi);
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
        
        if (!this.id.equals(other.id)) {
            return false;
        }
        
        return !(!this.id.equals(other.id) && !this.nama.equals(other.nama) && !this.id_propinsi.equals(other.id_propinsi));
    }

    @Override
    public String toString() {
        if(id == null) {
            return null;
        }			

        return "KabupatenDTO{" + "id=" + this.id + ", nama=" + this.nama + ", id_propinsi=" + this.id_propinsi + "}";	    
    }

}
