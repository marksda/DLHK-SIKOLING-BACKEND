package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
import com.cso.sikoling.main.repository.security.OtorisasiData;
import com.cso.sikoling.main.util.JsonbAttributeConverter;
import jakarta.json.JsonObject;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "transaksi.tbl_register_dokumen")
@NamedQueries({
    @NamedQuery(name = "RegisterDokumenData.findAll", query = "SELECT r FROM RegisterDokumenData r"),
    @NamedQuery(name = "RegisterDokumenData.findByPerusahaan", query = "SELECT r FROM RegisterDokumenData r WHERE r.perusahaan = :perusahaan"),
    @NamedQuery(name = "RegisterDokumenData.findByDokumen", query = "SELECT r FROM RegisterDokumenData r WHERE r.dokumen = :dokumen"),
    @NamedQuery(name = "RegisterDokumenData.findByTanggalRegistrasi", query = "SELECT r FROM RegisterDokumenData r WHERE r.tanggalRegistrasi = :tanggalRegistrasi"),
    @NamedQuery(name = "RegisterDokumenData.findByUploader", query = "SELECT r FROM RegisterDokumenData r WHERE r.uploader = :uploader"),
    @NamedQuery(name = "RegisterDokumenData.findByNamaFile", query = "SELECT r FROM RegisterDokumenData r WHERE r.namaFile = :namaFile"),
    @NamedQuery(name = "RegisterDokumenData.findById", query = "SELECT r FROM RegisterDokumenData r WHERE r.id = :id"),
    @NamedQuery(name = "RegisterDokumenData.findByStatusDokumen", query = "SELECT r FROM RegisterDokumenData r WHERE r.statusDokumen = :statusDokumen"),
    @NamedQuery(name = "RegisterDokumenData.findByIdLama", query = "SELECT r FROM RegisterDokumenData r WHERE r.idLama = :idLama"),
    @NamedQuery(name = "RegisterDokumenData.findByIsValidated", query = "SELECT r FROM RegisterDokumenData r WHERE r.isValidated = :isValidated"),
    @NamedQuery(name = "RegisterDokumenData.updateId", query = "UPDATE RegisterDokumenData SET id = :idBaru WHERE id = :idLama")})
public class RegisterDokumenData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "perusahaan", referencedColumnName = "id")
    @ManyToOne
    private PerusahaanData perusahaan;
       
    @JoinColumn(name = "dokumen", referencedColumnName = "id")
    @ManyToOne
    private MasterDokumenData dokumen;
    
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;
    
    @JoinColumn(name = "uploader", referencedColumnName = "id")
    @ManyToOne
    private OtorisasiData uploader;
    
    @Size(max = 2147483647)
    @Column(name = "nama_file")
    private String namaFile;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id")
    private String id;
    
    @JoinColumn(name = "status_dokumen", referencedColumnName = "id")
    @ManyToOne
    private StatusDokumenData statusDokumen;
    
    @Size(max = 2147483647)
    @Column(name = "id_lama")
    private String idLama;
    
    @Column(name = "is_validated")
    private Boolean isValidated;
    
    @Convert(converter = JsonbAttributeConverter.class)
    @Column(columnDefinition = "jsonb", name = "meta_file")
    private JsonObject metaFile;
    
    @Convert(converter = JsonbAttributeConverter.class)
    @Column(columnDefinition = "jsonb", name = "meta_info")
    private JsonObject metaInfo;

    public RegisterDokumenData() {
    }

    public RegisterDokumenData(String id) {
        this.id = id;
    }

    public RegisterDokumenData(String id, PerusahaanData perusahaan, MasterDokumenData dokumen) {
        this.id = id;
        this.perusahaan = perusahaan;
        this.dokumen = dokumen;
    }

    public PerusahaanData getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanData perusahaan) {
        this.perusahaan = perusahaan;
    }

    public MasterDokumenData getDokumen() {
        return dokumen;
    }

    public void setDokumen(MasterDokumenData dokumen) {
        this.dokumen = dokumen;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public void setTanggalRegistrasi(Date tanggalRegistrasi) {
        this.tanggalRegistrasi = tanggalRegistrasi;
    }

    public OtorisasiData getUploader() {
        return uploader;
    }

    public void setUploader(OtorisasiData uploader) {
        this.uploader = uploader;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusDokumenData getStatusDokumen() {
        return statusDokumen;
    }

    public void setStatusDokumen(StatusDokumenData statusDokumen) {
        this.statusDokumen = statusDokumen;
    }

    public String getIdLama() {
        return idLama;
    }

    public void setIdLama(String idLama) {
        this.idLama = idLama;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public JsonObject getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(JsonObject metaFile) {
        this.metaFile = metaFile;
    }

    public JsonObject getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(JsonObject metaInfo) {
        this.metaInfo = metaInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisterDokumenData)) {
            return false;
        }
        RegisterDokumenData other = (RegisterDokumenData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.RegisterDokumenData[ id=" + id + " ]";
    }

}
