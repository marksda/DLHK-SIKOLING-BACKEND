package com.cso.sikoling.abstraction.entity.permohonan;

import java.io.Serializable;
import java.util.Objects;

public class StatusPermohonan implements Serializable {
    
    private final String id;
    private final String nama;

    public StatusPermohonan(String id, String nama) {
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

        final StatusPermohonan other = (StatusPermohonan) obj;

        if (!this.id.equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "StatusPermohonan {"
                .concat("id=")
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }	

}
