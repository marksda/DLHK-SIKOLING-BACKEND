package com.cso.sikoling.abstraction.entity.permohonan;

import java.io.Serializable;
import java.util.Objects;

public class KategoriPermohonan implements Serializable {
    
    private final String id;
    private final String nama;
    private final String id_lama;

    public KategoriPermohonan(String id, String nama, String id_lama) {
        this.id = id;
        this.nama = nama;
        this.id_lama = id_lama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getId_lama() {
        return id_lama;
    }
	
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 137 * hash + Objects.hashCode(this.id);
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

        final KategoriPermohonan other = (KategoriPermohonan) obj;

        return this.id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "KategoriPermohonan{id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }

}
