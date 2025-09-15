package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import java.util.Objects;

public class VersiKbliDTO {

    private String id;
    private String nama;

    public VersiKbliDTO() {
    }
    
    public VersiKbliDTO(VersiKbli t ) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
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
    
    public VersiKbli toVersiKbli() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json versi kbli tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            if(isDigit) {
                return new VersiKbli(this.id, this.nama);
            }
            else {
                throw new IllegalArgumentException("id versi kbli harus bilangan panjang 2 digit");
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
        final VersiKbliDTO other = (VersiKbliDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "VersiKbliDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
    
    
}
