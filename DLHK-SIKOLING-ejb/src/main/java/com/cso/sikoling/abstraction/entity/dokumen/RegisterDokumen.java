package com.cso.sikoling.abstraction.entity.dokumen;

import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import jakarta.json.JsonObject;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class RegisterDokumen implements Serializable {
    
    private final String id;
    private final Perusahaan perusahaan;
    private final Dokumen dokumen;
    private final Date tanggalRegistrasi;
    private final Otorisasi uploader;
    private final String namaFile;
    private final StatusDokumen statusDokumen;
    private final String idLama;
    private final Boolean isValidated;
    private final JsonObject metaFile;

    public RegisterDokumen(String id, Perusahaan perusahaan, Dokumen dokumen, 
            Date tanggalRegistrasi, Otorisasi uploader, String namaFile, 
            StatusDokumen statusDokumen, String idLama, Boolean isValidated) {
        this.id = id;
        this.perusahaan = perusahaan;
        this.dokumen = dokumen;
        this.tanggalRegistrasi = tanggalRegistrasi;
        this.uploader = uploader;
        this.namaFile = namaFile;
        this.statusDokumen = statusDokumen;
        this.idLama = idLama;
        this.isValidated = isValidated;
        this.metaFile = null;
    }        
    
    public RegisterDokumen(String id, Perusahaan perusahaan, Dokumen dokumen, 
            Date tanggalRegistrasi, Otorisasi uploader, String namaFile, 
            StatusDokumen statusDokumen, String idLama, Boolean isValidated, JsonObject metaFile) {
        this.id = id;
        this.perusahaan = perusahaan;
        this.dokumen = dokumen;
        this.tanggalRegistrasi = tanggalRegistrasi;
        this.uploader = uploader;
        this.namaFile = namaFile;
        this.statusDokumen = statusDokumen;
        this.idLama = idLama;
        this.isValidated = isValidated;
        this.metaFile = metaFile;
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

    public String getNamaFile() {
        return namaFile;
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

    public JsonObject getMetaFile() {
        return metaFile;
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
