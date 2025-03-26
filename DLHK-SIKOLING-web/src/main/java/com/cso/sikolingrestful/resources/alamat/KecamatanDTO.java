package com.cso.sikolingrestful.resources.alamat;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.Kecamatan;

public class KecamatanDTO {
    
    private String id;
    private String nama;
    private String id_kabupaten;

    public KecamatanDTO() {
    }

    public KecamatanDTO(String id, String nama, String id_kabupaten) {
        this.id = id;
        this.nama = nama;
        this.id_kabupaten = id_kabupaten;
    }
    
    public KecamatanDTO(Kecamatan t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.id_kabupaten = t.getId_kabupaten();
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

    public String getId_kabupaten() {
        return id_kabupaten;
    }

    public void setId_kabupaten(String id_kabupaten) {
        this.id_kabupaten = id_kabupaten;
    }    
    
    public Kecamatan toKecamatan() {
        
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json kecamatan tidak sesuai");
        }
        else {
            return new Kecamatan(this.id, this.nama, this.id_kabupaten);
        }
        
    }
    
    @Override
    public int hashCode() {
        
        int hash = 753;
        hash = 73 * hash + Objects.hashCode(this.id );
        hash = 73 * hash + Objects.hashCode(this.nama);
        hash = 73 * hash + Objects.hashCode(this.id_kabupaten);
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

        final KecamatanDTO other = (KecamatanDTO) obj;
        
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        
        return !(this.id_kabupaten == null ? other.id_kabupaten != null : !this.id_kabupaten.equals(other.id_kabupaten));
        
    }

    @Override
    public String toString() {
        
        if(id == null) {
            return null;
        }			

        return "KecamatanDTO{" + "id=" + this.id + ", id_kabupaten=" + this.id_kabupaten + ", nama=" + this.nama + "}";	   
        
    }

}
