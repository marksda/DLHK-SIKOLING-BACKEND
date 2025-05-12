package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Objects;


public class Jwa implements Serializable {
    private final String id;
    private final String nama;
    private final String keterangan;
    private final String id_jwa_type;

    public Jwa(String id, String nama, String keterangan, String id_jwa_type) {
        this.id = id;
        this.nama = nama;
        this.keterangan = keterangan;
        this.id_jwa_type = id_jwa_type;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getId_jwa_type() {
        return id_jwa_type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Jwa other = (Jwa) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Jwa{" + "id=" + id + ", nama" + this.nama + "}";
    }
     

}
