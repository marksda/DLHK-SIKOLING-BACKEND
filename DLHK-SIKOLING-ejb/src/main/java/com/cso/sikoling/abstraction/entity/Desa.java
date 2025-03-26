package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;


public class Desa implements Serializable {
    
    private final String id;
    private final String nama;
    private final String id_kecamatan;

    public Desa(String id, String nama, String id_kecamatan) {
        this.id = id;
        this.nama = nama;
        this.id_kecamatan = id_kecamatan;
    }
    
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getId_kecamatan() {
        return id_kecamatan;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nama);
        hash = 13 * hash + Objects.hashCode(this.id_kecamatan);
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

        return this.id_kecamatan.equals(other.id_kecamatan);
        
    }

    @Override
    public String toString() {
        return "Desa{" + "id=" + id + ", id_kecamatan=" + this.id_kecamatan + ", nama" + this.nama + "}";
    }

}
