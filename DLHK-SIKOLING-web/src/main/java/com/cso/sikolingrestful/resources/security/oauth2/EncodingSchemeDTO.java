package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.EncodingScheme;
import java.util.Objects;


public class EncodingSchemeDTO {
    private String id;
    private String nama;

    public EncodingSchemeDTO() {
    }
    
    public EncodingSchemeDTO(EncodingScheme t) {
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
    
    public EncodingScheme toEncodingScheme() {
        return new EncodingScheme(this.id, this.nama);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final EncodingSchemeDTO other = (EncodingSchemeDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Encoding scheme{" + "id=" + id + ", nama" + this.nama + "}";
    }
    
    
}
