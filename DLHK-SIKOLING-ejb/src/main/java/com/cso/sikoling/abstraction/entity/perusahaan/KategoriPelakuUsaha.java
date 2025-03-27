package com.cso.sikoling.abstraction.entity.perusahaan;

import java.io.Serializable;
import java.util.Objects;

public class KategoriPelakuUsaha implements Serializable {
    
    private final String id;
    private final String nama;
    private final KategoriSkalaUsaha kategoriSkalaUsaha;

    public KategoriPelakuUsaha(String id, String nama, KategoriSkalaUsaha kategoriSkalaUsaha) {
            this.id = id;
            this.nama = nama;
            this.kategoriSkalaUsaha = kategoriSkalaUsaha;
    }

    public String getId() {
            return id;
    }

    public String getNama() {
            return nama;
    }

    public KategoriSkalaUsaha getKategoriSkalaUsaha() {
        return kategoriSkalaUsaha;
    }

    @Override
    public int hashCode() {

        int hash = 71;
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

        final KategoriPelakuUsaha other = (KategoriPelakuUsaha) obj;

        return this.id.equals(other.id);
        
    }

    @Override
    public String toString() {
        return "JenisPelakuUsaha{"
                .concat("id=")
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }
	
}
