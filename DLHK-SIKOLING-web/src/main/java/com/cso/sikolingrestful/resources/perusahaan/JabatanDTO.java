package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import java.util.Objects;


public class JabatanDTO {

    private String id;
    private String nama;

    public JabatanDTO() {
    }
    
    public JabatanDTO(Jabatan t) {
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
    
    public Jabatan toJabatan() {
        if( this.id == null) {
            throw new IllegalArgumentException("format data json jabatan tidak sesuai");
        }
        else {
            boolean isDigit = this.id.matches("[0-9]+");
            
            if(isDigit) {  
                return new Jabatan(id, nama);
            }
            else {
                throw new IllegalArgumentException("id jabatan harus bilangan panjang 3 digit");
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final JabatanDTO other = (JabatanDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "JabatanDTO{ id="
                .concat(this.id)
                .concat(", nama=")
                .concat(this.nama)
                .concat("}");
    }
    
}
