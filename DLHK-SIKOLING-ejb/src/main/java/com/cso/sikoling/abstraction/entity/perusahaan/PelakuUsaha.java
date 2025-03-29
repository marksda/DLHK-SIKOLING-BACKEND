package com.cso.sikoling.abstraction.entity.perusahaan;

import java.io.Serializable;
import java.util.Objects;

public class PelakuUsaha implements Serializable {
    
    private final String id;
    private final String nama;
    private final String singkatan;
    private final KategoriPelakuUsaha kategori_pelaku_usaha;

    public PelakuUsaha(String id, String nama, String singkatan, KategoriPelakuUsaha kategori_pelaku_usaha) {

        this.id = id;
        this.nama = nama;
        this.singkatan = singkatan;
        this.kategori_pelaku_usaha = kategori_pelaku_usaha;

    }
    

    public KategoriPelakuUsaha getKategori_pelaku_usaha() {
        return kategori_pelaku_usaha;
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
        
        int hash = 91;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.nama);
        hash = 11 * hash + Objects.hashCode(this.singkatan);

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

        final PelakuUsaha other = (PelakuUsaha) obj;

        if (!this.id.equals(other.getId())) {
            return false;
        }

        return this.nama.equals(other.nama);
        
    }

    @Override
    public String toString() {
        return "DetailPelakuUsaha{id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat(", singkatan=")
                .concat(this.singkatan)
                .concat("}");
    }	

}
