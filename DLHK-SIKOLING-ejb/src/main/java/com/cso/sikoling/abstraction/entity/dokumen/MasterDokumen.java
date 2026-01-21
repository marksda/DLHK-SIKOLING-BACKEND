package com.cso.sikoling.abstraction.entity.dokumen;

import java.io.Serializable;
import java.util.Objects;


public class MasterDokumen implements Serializable {
    
    private final String id;
    private final String nama;
    private final String singkatan;
    private final Short id_lama;

    public MasterDokumen(String id, String nama, String singkatan, Short id_lama) {
        this.id = id;
        this.nama = nama;
        this.singkatan = singkatan;
        this.id_lama = id_lama;
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

    public Short getId_lama() {
        return id_lama;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final MasterDokumen other = (MasterDokumen) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Dokumen{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }

}
