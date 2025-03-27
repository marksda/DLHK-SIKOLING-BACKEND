package com.cso.sikoling.abstraction.entity.person;

import java.io.Serializable;
import java.util.Objects;


public class JenisKelamin implements Serializable {
    
    private final String id;
    private final String nama;

    public JenisKelamin(String id, String nama) {
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
        
        int hash = 11;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nama);
        
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

        final JenisKelamin other = (JenisKelamin) obj;

        if (!this.id.equals(other.id)) {
            return false;
        }

        return this.nama.equals(other.nama);
    }

    @Override
    public String toString() {
        return "JenisKelamin{" + "id=" + id + "nama=" + nama + "}";
    }
        
}
