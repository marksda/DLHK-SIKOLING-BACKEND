package com.cso.sikoling.abstraction.entity.permohonan;

import java.io.Serializable;
import java.util.Objects;


public class KategoriPengurusPermohonan implements Serializable {
    
    private final String id;
    private final String nama;

    public KategoriPengurusPermohonan(String id, String nama) {
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
        final KategoriPengurusPermohonan other = (KategoriPengurusPermohonan) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "KategoriPengurusPermohonan{id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }
}
