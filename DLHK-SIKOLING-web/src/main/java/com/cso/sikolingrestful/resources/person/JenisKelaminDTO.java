package com.cso.sikolingrestful.resources.person;


import java.util.Objects;
import com.cso.sikoling.abstraction.entity.JenisKelamin;

public class JenisKelaminDTO {
    
    private String id;
    private String nama;

    public JenisKelaminDTO() {
    }

    public JenisKelaminDTO(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    
    public JenisKelaminDTO(JenisKelamin t) {
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
    
    public JenisKelamin toJenisKelamin() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json jenis kelamin tidak sesuai");
        }
        else {
            return new JenisKelamin(this.id, this.nama);
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

        final JenisKelaminDTO other = (JenisKelaminDTO) obj;
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

        return "JenisKelaminDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
