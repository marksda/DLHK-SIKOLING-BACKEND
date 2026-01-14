package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
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
@Table(name = "transaksi.tbl_register_dokumen_sementara")
@NamedQueries({
    @NamedQuery(name = "RegisterDokumenSementaraData.findAll", query = "SELECT r FROM RegisterDokumenSementaraData r"),
    @NamedQuery(name = "RegisterDokumenSementaraData.findById", query = "SELECT r FROM RegisterDokumenSementaraData r WHERE r.id = :id"),
    @NamedQuery(name = "RegisterDokumenSementaraData.findByNamaFile", query = "SELECT r FROM RegisterDokumenSementaraData r WHERE r.namaFile = :namaFile"),
    @NamedQuery(name = "RegisterDokumenSementaraData.findByTanggal", query = "SELECT r FROM RegisterDokumenSementaraData r WHERE r.tanggal = :tanggal"),
    @NamedQuery(name = "RegisterDokumenSementaraData.updateId", query = "UPDATE RegisterDokumenSementaraData SET id = :idBaru WHERE id = :idLama")})
public class RegisterDokumenSementaraData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama_file")
    private String namaFile;
    
    @Column(name = "tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;
    
    @JoinColumn(name = "dokumen", referencedColumnName = "id")
    @ManyToOne
    private DokumenData dokumen;
    
    @JoinColumn(name = "perusahaan", referencedColumnName = "id")
    @ManyToOne
    private PerusahaanData perusahaan;
    
    @Convert(converter = JsonbAttributeConverter.class)
    @Column(columnDefinition = "jsonb", name = "meta_file")
    private JsonObject metaFile;

    public RegisterDokumenSementaraData() {
    }

    public RegisterDokumenSementaraData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public DokumenData getDokumen() {
        return dokumen;
    }

    public void setDokumen(DokumenData dokumen) {
        this.dokumen = dokumen;
    }

    public PerusahaanData getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanData perusahaan) {
        this.perusahaan = perusahaan;
    }

    public JsonObject getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(JsonObject metaFile) {
        this.metaFile = metaFile;
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
        if (!(object instanceof RegisterDokumenSementaraData)) {
            return false;
        }
        RegisterDokumenSementaraData other = (RegisterDokumenSementaraData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.RegisterDokumenSementaraData[ id=" + id + " ]";
    }

}
