package com.cso.sikoling.abstraction.entity.permohonan;

import java.io.Serializable;
import java.util.Objects;

public class PosisiTahapPemberkasan implements Serializable {
    
    private final String id;
    private final String nama;
    private final String keterangan;

    public PosisiTahapPemberkasan(String id, String nama, String keterangan) {
        this.id = id;
        this.nama = nama;
        this.keterangan = keterangan;
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

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 171 * hash + Objects.hashCode(this.id);
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

        final PosisiTahapPemberkasan other = (PosisiTahapPemberkasan) obj;

        if (!this.id.equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "HakAkses {"
                .concat("id=")
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat(", keterangan=")
                .concat(this.keterangan)
                .concat("}");
    }	

}
