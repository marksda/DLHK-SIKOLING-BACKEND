package com.cso.sikoling.abstraction.entity.alamat;

import java.io.Serializable;
import java.util.Objects;

public class Kecamatan implements Serializable {

    private final String id;
    private final String nama;
    private final String id_propinsi;
    private final String id_kabupaten;

    public Kecamatan(String id, String nama, String id_propinsi, String id_kabupaten) {
        this.id = id;
        this.nama = nama;
        this.id_propinsi = id_propinsi;
        this.id_kabupaten = id_kabupaten;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getId_propinsi() {
        return id_propinsi;
    }

    public String getId_kabupaten() {
        return id_kabupaten;
    }    
    
    @Override
    public int hashCode() {
        
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nama);
        hash = 13 * hash + Objects.hashCode(this.id_kabupaten);
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

        final Kecamatan other = (Kecamatan) obj;

        if (!this.id.equals(other.id)) {
            return false;
        }

        return this.nama.equals(other.id_kabupaten);
        
    }

    @Override
    public String toString() {
        return "Kecamatan{" + "id=" + id + ", nama=" + nama + ", id_kabupaten=" + this.id_kabupaten + "}";
    }
        
}