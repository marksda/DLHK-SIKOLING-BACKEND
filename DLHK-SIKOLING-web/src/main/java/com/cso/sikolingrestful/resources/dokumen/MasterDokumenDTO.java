package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.MasterDokumen;
import java.util.Objects;

public class MasterDokumenDTO {

    private String id;
    private String nama;
    private String singkatan;
    private Short id_lama;

    public MasterDokumenDTO() {
    }
    
    public MasterDokumenDTO(MasterDokumen t ) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.singkatan = t.getSingkatan();
            this.id_lama = t.getId_lama();
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

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public Short getId_lama() {
        return id_lama;
    }

    public void setId_lama(Short id_lama) {
        this.id_lama = id_lama;
    }
    
    public MasterDokumen toDokumen() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json dokumen tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            if(isDigit) {
                return new MasterDokumen(this.id, this.nama, this.singkatan, this.id_lama);
            }
            else {
                throw new IllegalArgumentException("id dokumen harus bilangan panjang 2 digit");
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
        final MasterDokumenDTO other = (MasterDokumenDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "DokumenDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(nama)
                .concat("}");
    }
    
    
}
