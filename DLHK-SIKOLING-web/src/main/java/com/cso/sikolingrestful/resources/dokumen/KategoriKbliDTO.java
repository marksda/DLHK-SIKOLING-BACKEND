package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import java.util.Objects;

public class KategoriKbliDTO {

    private String id;
    private String nama;
    private VersiKbliDTO versi_kbli;

    public KategoriKbliDTO() {
    }
    
    public KategoriKbliDTO(KategoriKbli t ) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.versi_kbli = t.getVersiKbli() != null ?
                                new VersiKbliDTO(t.getVersiKbli()) : null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public VersiKbliDTO getVersi_kbli() {
        return versi_kbli;
    }

    public void setVersi_kbli(VersiKbliDTO versi_kbli) {
        this.versi_kbli = versi_kbli;
    }
    
    public KategoriKbli toKategoriKbli() {
        if( this.id == null || this.nama == null || this.versi_kbli == null) {
            throw new IllegalArgumentException("format data json versi kbli tidak sesuai");
        }
        else {
            boolean isOneAbjad = this.id.matches("[a-zA-Z]");
            if(isOneAbjad) {
                VersiKbli versiKbli = versi_kbli != null ?
                                        versi_kbli.toVersiKbli() : null;
                return new KategoriKbli(this.id, this.nama, versiKbli);
            }
            else {
                throw new IllegalArgumentException("id kategori kbli harus abjad panjang 1 digit");
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final KategoriKbliDTO other = (KategoriKbliDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "KategoriKbliDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
    
    
}
