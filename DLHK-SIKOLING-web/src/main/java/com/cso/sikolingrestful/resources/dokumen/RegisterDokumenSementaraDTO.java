package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegisterDokumenSementaraDTO {

    private String id;
    private String id_jenis_dokumen;
    private String id_perusahaan;
    private String nama_file;
    private String tanggal;
    private MetaFileDTO metaFile;

    public RegisterDokumenSementaraDTO() {
    }
    
    public RegisterDokumenSementaraDTO(RegisterDokumenSementara t ) {
        if(t != null) {
            this.id = t.getId();
            this.id_jenis_dokumen = t.getIdJenisDokumen();
            this.id_perusahaan = t.getIdPerusahaan();
            this.nama_file = t.getNamaFile();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date tmpTglReg = t.getTanggal();
            this.tanggal = tmpTglReg != null ? df.format(tmpTglReg) : null;
            this.metaFile = t.getMetaFile() != null ?
                    new MetaFileDTO(t.getMetaFile()) : null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_jenis_dokumen() {
        return id_jenis_dokumen;
    }

    public void setId_jenis_dokumen(String id_jenis_dokumen) {
        this.id_jenis_dokumen = id_jenis_dokumen;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public MetaFileDTO getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(MetaFileDTO metaFile) {
        this.metaFile = metaFile;
    }

    public String getId_perusahaan() {
        return id_perusahaan;
    }

    public void setId_perusahaan(String id_perusahaan) {
        this.id_perusahaan = id_perusahaan;
    }
    
    public RegisterDokumenSementara toRegisterDokumenSementara() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        try {
            Date date = this.tanggal != null ? df.parse(this.tanggal) : null;
            return new RegisterDokumenSementara(
                    this.id, 
                    this.id_jenis_dokumen, 
                    this.id_perusahaan,
                    this.nama_file, 
                    date, 
                    this.metaFile != null ? 
                            this.metaFile.toMetaFile() : null
                );
        }
        catch (ParseException ex) {
            return new RegisterDokumenSementara(
                    this.id, 
                    this.id_jenis_dokumen, 
                    this.id_perusahaan,
                    this.nama_file, 
                    null,
                    this.metaFile != null ? 
                            this.metaFile.toMetaFile() : null
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
        final RegisterDokumenSementaraDTO other = (RegisterDokumenSementaraDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "RegisterDokumenSementaraDTO{ id="
                .concat(this.id)
                .concat(", nama_file=")
                .concat(nama_file)
                .concat("}");
    }
    
    
}
