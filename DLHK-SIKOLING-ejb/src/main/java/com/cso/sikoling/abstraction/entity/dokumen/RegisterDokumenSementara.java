package com.cso.sikoling.abstraction.entity.dokumen;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class RegisterDokumenSementara implements Serializable {
    
    private final String id;
    private final String idJenisDokumen;
    private final String namaFile;
     private final Date tanggal;

    public RegisterDokumenSementara(String id, String idJenisDokumen, 
            String namaFile, Date tanggal) {
        this.id = id;
        this.idJenisDokumen = idJenisDokumen;
        this.namaFile = namaFile;
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }
    
    public String getNamaFile() {
        return namaFile;
    }

    public String getIdJenisDokumen() {
        return idJenisDokumen;
    }

    public Date getTanggal() {
        return tanggal;
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
        final RegisterDokumenSementara other = (RegisterDokumenSementara) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Dokumen{ id="
                .concat(this.id)
                .concat("}");
    }

}
