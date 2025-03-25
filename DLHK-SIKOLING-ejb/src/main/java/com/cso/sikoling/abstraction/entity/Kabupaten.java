
package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;


public class Kabupaten implements Serializable {
    
    private final String id;
    private final String nama;
    private final String id_propinsi;

    public Kabupaten(String id, String nama, String id_propinsi) {
        this.id = id;
        this.nama = nama;
        this.id_propinsi = id_propinsi;
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
    
    @Override
    public String toString() {
        return "Kabupaten{" + "id=" + id + ", nama=" + nama + ", id_propinsi=" + this.id_propinsi + "}";
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.nama);
        hash = 19 * hash + Objects.hashCode(this.id_propinsi);
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

        return !(!this.id.equals(other.id) && !this.nama.equals(other.nama) && !this.id_propinsi.equals(other.id_propinsi));
    }

}
