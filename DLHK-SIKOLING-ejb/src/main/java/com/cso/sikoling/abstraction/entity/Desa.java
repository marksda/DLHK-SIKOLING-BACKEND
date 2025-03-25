package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;


public class Desa implements Serializable {
    
    private final String id;
    private final String nama;

    public Desa(String id, String nama) {
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
        
        int hash = 7;
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

        final Desa other = (Desa) obj;

        if (!this.id.equals(other.id)) {
            return false;
        }

        if (!this.nama.equals(other.nama)) {
            return false;
        }

        return true;
        
    }

    @Override
    public String toString() {
        return "Desa{" + "id=" + id + "nama=" + nama + "}";
    }

}
