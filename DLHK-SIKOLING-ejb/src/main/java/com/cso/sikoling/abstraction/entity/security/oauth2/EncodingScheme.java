package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Objects;


public class EncodingScheme implements Serializable {
    
    private final String id;
    private final String nama;

    public EncodingScheme(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
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
        final EncodingScheme other = (EncodingScheme) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Encoding scheme{" + "id=" + id + ", nama" + this.nama + "}";
    }
    
}
