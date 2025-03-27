package com.cso.sikolingrestful.resources.alamat;


import java.util.Objects;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;

public class PropinsiDTO {
    
    private String id;
    private String nama;

    public PropinsiDTO() {
    }

    public PropinsiDTO(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    
    public PropinsiDTO(Propinsi t) {
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
    
    public Propinsi toPropinsi() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json propinsi tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            if(isDigit) {
                return new Propinsi(this.id, this.nama);
            }
            else {
                throw new IllegalArgumentException("id propinsi harus bilangan panjang 2 digit");
            }
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 751;
        hash = 71 * hash + Objects.hashCode(id );
        hash = 71 * hash + Objects.hashCode(nama);
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

        final PropinsiDTO other = (PropinsiDTO) obj;
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

        return "PropinsiDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
