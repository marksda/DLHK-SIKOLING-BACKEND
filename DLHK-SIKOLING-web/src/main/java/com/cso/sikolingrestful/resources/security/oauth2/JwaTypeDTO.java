package com.cso.sikolingrestful.resources.security.oauth2;


import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
import java.util.Objects;

public class JwaTypeDTO {
    
    private String id;
    private String nama;

    public JwaTypeDTO() {
    }

    public JwaTypeDTO(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    
    public JwaTypeDTO(JwaType t) {
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
    
    public JwaType toJwaType() {
        return new JwaType(this.id, this.nama);
    }
    
    @Override
    public int hashCode() {
        int hash = 751;
        hash = 71 * hash + Objects.hashCode(id );
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

        final JwaTypeDTO other = (JwaTypeDTO) obj;
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

        return "JwaTypeDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
