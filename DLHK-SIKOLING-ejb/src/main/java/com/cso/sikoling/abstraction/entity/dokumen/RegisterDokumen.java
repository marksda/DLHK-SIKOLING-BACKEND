package com.cso.sikoling.abstraction.entity.dokumen;

import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class RegisterDokumen implements Serializable {
    
    private final String id;
    private final Perusahaan perusahaan;
    private final Dokumen dokumen;
    private final Date tanggalRegistrasi;
    private final Otorisasi uploader;
    private final String lokasiFile;
    private final StatusDokumen statusDokumen;
    private final String idLama;
    private final Boolean isValidated;

    public RegisterDokumen(String id, Perusahaan perusahaan, Dokumen dokumen, 
            Date tanggalRegistrasi, Otorisasi uploader, String lokasiFile, 
            StatusDokumen statusDokumen, String idLama, Boolean isValidated) {
        this.id = id;
        this.perusahaan = perusahaan;
        this.dokumen = dokumen;
        this.tanggalRegistrasi = tanggalRegistrasi;
        this.uploader = uploader;
        this.lokasiFile = lokasiFile;
        this.statusDokumen = statusDokumen;
        this.idLama = idLama;
        this.isValidated = isValidated;
    }    

    public String getId() {
        return id;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public Dokumen getDokumen() {
        return dokumen;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public Otorisasi getUploader() {
        return uploader;
    }

    public String getLokasiFile() {
        return lokasiFile;
    }

    public StatusDokumen getStatusDokumen() {
        return statusDokumen;
    }

    public String getIdLama() {
        return idLama;
    }

    public Boolean getIsValidated() {
        return isValidated;
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
        final RegisterDokumen other = (RegisterDokumen) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Dokumen{ id="
                .concat(this.id)
                .concat("}");
    }

}
