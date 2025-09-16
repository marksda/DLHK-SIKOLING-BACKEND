package com.cso.sikoling.abstraction.entity.dokumen;

import java.io.Serializable;
import java.util.Objects;


public class KategoriKbli implements Serializable {
    
    private final String id;
    private final String nama;
    private final VersiKbli versiKbli;

    public KategoriKbli(String id, String nama, VersiKbli versiKbli) {
        this.id = id;
        this.nama = nama;
        this.versiKbli = versiKbli;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public VersiKbli getVersiKbli() {
        return versiKbli;
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
        final KategoriKbli other = (KategoriKbli) obj;
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
