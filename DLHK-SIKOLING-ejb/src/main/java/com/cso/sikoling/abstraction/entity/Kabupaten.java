
package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;


public class Kabupaten implements Serializable {
    
    private final String id;
    private final String nama;

    public Kabupaten(String id, String nama) {
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
    public String toString() {
        return "Kabupaten{" + "id=" + id + "nama=" + nama + "}";
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.nama);
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

        final Kabupaten other = (Kabupaten) obj;

        if (!this.id.equals(other.id)) {
            return false;
        }

        if (!this.nama.equals(other.nama)) {
            return false;
        }

        return true;
    }

}
