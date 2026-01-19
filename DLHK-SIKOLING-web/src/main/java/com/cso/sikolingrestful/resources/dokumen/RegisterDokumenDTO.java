package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikolingrestful.resources.perusahaan.PerusahaanDTO;
import com.cso.sikolingrestful.resources.security.OtorisasiDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegisterDokumenDTO {

    private String id;
    private PerusahaanDTO perusahaan;
    private DokumenDTO dokumen;
    private String tanggal_registrasi;
    private OtorisasiDTO uploader;
    private String nama_file;
    private StatusDokumenDTO status_dokumen;
    private String id_lama;
    private Boolean is_validated;

    public RegisterDokumenDTO() {
    }
    
    public RegisterDokumenDTO(RegisterDokumen t ) {
        if(t != null) {
            this.id = t.getId();
            this.perusahaan = t.getPerusahaan() != null ?
                    new PerusahaanDTO(t.getPerusahaan()) : null;
            this.dokumen = t.getDokumen() != null ?
                    new DokumenDTO(t.getDokumen()) : null;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date tmpTglReg = t.getTanggalRegistrasi();
            this.tanggal_registrasi = tmpTglReg != null ? 
                    df.format(tmpTglReg) : null;
            this.uploader = t.getUploader() != null ?
                    new OtorisasiDTO(t.getUploader()) : null;
            this.nama_file = t.getNamaFile();
            this.status_dokumen = t.getStatusDokumen() != null ?
                    new StatusDokumenDTO(t.getStatusDokumen()) : null;
            this.id_lama = t.getIdLama();
            this.is_validated = t.getIsValidated();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PerusahaanDTO getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanDTO perusahaan) {
        this.perusahaan = perusahaan;
    }

    public DokumenDTO getDokumen() {
        return dokumen;
    }

    public void setDokumen(DokumenDTO dokumen) {
        this.dokumen = dokumen;
    }

    public String getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(String tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public OtorisasiDTO getUploader() {
        return uploader;
    }

    public void setUploader(OtorisasiDTO uploader) {
        this.uploader = uploader;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public StatusDokumenDTO getStatus_dokumen() {
        return status_dokumen;
    }

    public void setStatus_dokumen(StatusDokumenDTO status_dokumen) {
        this.status_dokumen = status_dokumen;
    }

    public String getId_lama() {
        return id_lama;
    }

    public void setId_lama(String id_lama) {
        this.id_lama = id_lama;
    }

    public Boolean getIs_validated() {
        return is_validated;
    }

    public void setIs_validated(Boolean is_validated) {
        this.is_validated = is_validated;
    }
    
    public RegisterDokumen toRegisterDokumen() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = this.tanggal_registrasi != null ? df.parse(this.tanggal_registrasi) : null;
            return new RegisterDokumen(
                this.id, 
                this.perusahaan != null ? this.perusahaan.toPerusahaan() : null, 
                this.dokumen != null ? this.dokumen.toDokumen() : null, 
                date, 
                this.uploader != null ? this.uploader.toOtorisasi() : null, 
                this.nama_file, 
                this.status_dokumen != null ? this.status_dokumen.toStatusDokumen() : null, 
                this.id_lama, 
                this.is_validated
            );
        } catch (ParseException e) {
            return new RegisterDokumen(
                this.id, 
                this.perusahaan != null ? this.perusahaan.toPerusahaan() : null, 
                this.dokumen != null ? this.dokumen.toDokumen() : null, 
                null, 
                this.uploader != null ? this.uploader.toOtorisasi() : null, 
                this.nama_file, 
                this.status_dokumen != null ? this.status_dokumen.toStatusDokumen() : null, 
                this.id_lama, 
                this.is_validated
            );
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
        final RegisterDokumenDTO other = (RegisterDokumenDTO) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "RegisterDokumenDTO{ id="
                .concat(this.id)
                .concat("}");
    }
    
}
