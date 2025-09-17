package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.Kbli;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import java.util.Objects;

public class KbliDTO {

    private String id;
    private String nama;
    private VersiKbliDTO versi_kbli;
    private KategoriKbliDTO kategori_kbli;

    public KbliDTO() {
    }
    
    public KbliDTO(Kbli t ) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.versi_kbli = t.getVersiKbli() != null ?
                                new VersiKbliDTO(t.getVersiKbli()) : null;
            this.kategori_kbli = t.getKategoriKbli() != null ?
                                new KategoriKbliDTO(t.getKategoriKbli()) : null;
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

    public KategoriKbliDTO getKategori_kbli() {
        return kategori_kbli;
    }

    public void setKategori_kbli(KategoriKbliDTO kategori_kbli) {
        this.kategori_kbli = kategori_kbli;
    }
    
    public Kbli toKbli() {
        if( this.id == null || this.nama == null || 
                this.versi_kbli == null || this.kategori_kbli == null) {
            throw new IllegalArgumentException("format data json kbli tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]{5}");
            if(isDigit) {
                VersiKbli versiKbli = versi_kbli != null ?
                                        versi_kbli.toVersiKbli() : null;
                KategoriKbli kategoriKbli = kategori_kbli != null ?
                                        kategori_kbli.toKategoriKbli() : null;
                return new Kbli(this.id, this.nama, versiKbli, kategoriKbli);
            }
            else {
                throw new IllegalArgumentException("id kbli harus bilangan 5 digit");
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
        final KbliDTO other = (KbliDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "KbliDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
    
    
}
