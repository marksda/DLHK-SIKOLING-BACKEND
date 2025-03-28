package com.cso.sikoling.abstraction.entity.perusahaan;

import java.io.Serializable;
import java.util.Objects;

public class KategoriModelPerizinan implements Serializable {
    
    private final String id;
    private final String nama;
    private final String singkatan;

    public KategoriModelPerizinan(String id, String nama, String singkatan) {
        this.id = id;
        this.nama = nama;
        this.singkatan = singkatan;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getSingkatan() {
        return singkatan;
    }

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 17 * hash + Objects.hashCode(this.id);
        
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

        final KategoriModelPerizinan other = (KategoriModelPerizinan) obj;

        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "ModelPerizinan{"
                .concat("id=")
                .concat(id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }

}
