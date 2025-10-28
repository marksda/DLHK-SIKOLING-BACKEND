package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
import com.cso.sikoling.main.repository.security.OtorisasiData;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
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
@Table(name = "transaksi.tbl_register_permohonan")
@NamedQueries({
    @NamedQuery(name = "RegisterPermohonanData.findAll", query = "SELECT r FROM RegisterPermohonanData r"),
    @NamedQuery(name = "RegisterPermohonanData.findById", query = "SELECT r FROM RegisterPermohonanData r WHERE r.id = :id"),
    @NamedQuery(name = "RegisterPermohonanData.findByKategoriPermohonan", query = "SELECT r FROM RegisterPermohonanData r WHERE r.kategoriPermohonan = :kategoriPermohonan"),
    @NamedQuery(name = "RegisterPermohonanData.findByTanggalRegistrasi", query = "SELECT r FROM RegisterPermohonanData r WHERE r.tanggalRegistrasi = :tanggalRegistrasi"),
    @NamedQuery(name = "RegisterPermohonanData.findByPerusahaan", query = "SELECT r FROM RegisterPermohonanData r WHERE r.perusahaan = :perusahaan"),
    @NamedQuery(name = "RegisterPermohonanData.findByPengakses", query = "SELECT r FROM RegisterPermohonanData r WHERE r.pengakses = :pengakses"),
    @NamedQuery(name = "RegisterPermohonanData.findByKategoriPengurusPermohonan", query = "SELECT r FROM RegisterPermohonanData r WHERE r.kategoriPengurusPermohonan = :kategoriPengurusPermohonan"),
    @NamedQuery(name = "RegisterPermohonanData.findByIdonlb", query = "SELECT r FROM RegisterPermohonanData r WHERE r.idonlb = :idonlb"),
    @NamedQuery(name = "RegisterPermohonanData.findByPosisiTahapPemberkasanPengirim", query = "SELECT r FROM RegisterPermohonanData r WHERE r.posisiTahapPemberkasanPengirim = :posisiTahapPemberkasanPengirim"),
    @NamedQuery(name = "RegisterPermohonanData.findByIdizin", query = "SELECT r FROM RegisterPermohonanData r WHERE r.idizin = :idizin"),
    @NamedQuery(name = "RegisterPermohonanData.findByPosisiTahapPemberkasanPenerima", query = "SELECT r FROM RegisterPermohonanData r WHERE r.posisiTahapPemberkasanPenerima = :posisiTahapPemberkasanPenerima"),
    @NamedQuery(name = "RegisterPermohonanData.findByStatusFlow", query = "SELECT r FROM RegisterPermohonanData r WHERE r.statusFlow = :statusFlow")})
public class RegisterPermohonanData implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;    
    
    @JoinColumn(name = "kategori_permohonan", referencedColumnName = "id")
    @ManyToOne
    private KategoriPermohonanData kategoriPermohonan;
    
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;
    
    @JoinColumn(name = "perusahaan", referencedColumnName = "id")
    @ManyToOne
    private PerusahaanData perusahaan;
    
    @JoinColumn(name = "pengakses", referencedColumnName = "id")
    @ManyToOne
    private OtorisasiData pengakses;
    
    @JoinColumn(name = "kategori_pengurus_permohonan", referencedColumnName = "id")
    @ManyToOne
    private KategoriPengurusPermohonanData kategoriPengurusPermohonan;
    
    @Size(max = 2147483647)
    @Column(name = "idonlb")
    private String idonlb;
    
    @JoinColumn(name = "posisi_tahap_pemberkasan_pengirim", referencedColumnName = "id")
    @ManyToOne
    private PosisiTahapPemberkasanData posisiTahapPemberkasanPengirim;
    
    @Size(max = 2147483647)
    @Column(name = "idizin")
    private String idizin;
    
    @JoinColumn(name = "posisi_tahap_pemberkasan_penerima", referencedColumnName = "id")
    @ManyToOne
    private PosisiTahapPemberkasanData posisiTahapPemberkasanPenerima;
    
    @JoinColumn(name = "status_flow", referencedColumnName = "id")
    @ManyToOne
    private StatusFlowJenisLogData statusFlow;

    public RegisterPermohonanData() {
    }

    public RegisterPermohonanData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KategoriPermohonanData getKategoriPermohonan() {
        return kategoriPermohonan;
    }

    public void setKategoriPermohonan(KategoriPermohonanData kategoriPermohonan) {
        this.kategoriPermohonan = kategoriPermohonan;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public void setTanggalRegistrasi(Date tanggalRegistrasi) {
        this.tanggalRegistrasi = tanggalRegistrasi;
    }

    public PerusahaanData getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanData perusahaan) {
        this.perusahaan = perusahaan;
    }

    public OtorisasiData getPengakses() {
        return pengakses;
    }

    public void setPengakses(OtorisasiData pengakses) {
        this.pengakses = pengakses;
    }

    public KategoriPengurusPermohonanData getKategoriPengurusPermohonan() {
        return kategoriPengurusPermohonan;
    }

    public void setKategoriPengurusPermohonan(KategoriPengurusPermohonanData kategoriPengurusPermohonan) {
        this.kategoriPengurusPermohonan = kategoriPengurusPermohonan;
    }

    public String getIdonlb() {
        return idonlb;
    }

    public void setIdonlb(String idonlb) {
        this.idonlb = idonlb;
    }

    public PosisiTahapPemberkasanData getPosisiTahapPemberkasanPengirim() {
        return posisiTahapPemberkasanPengirim;
    }

    public void setPosisiTahapPemberkasanPengirim(PosisiTahapPemberkasanData posisiTahapPemberkasanPengirim) {
        this.posisiTahapPemberkasanPengirim = posisiTahapPemberkasanPengirim;
    }

    public String getIdizin() {
        return idizin;
    }

    public void setIdizin(String idizin) {
        this.idizin = idizin;
    }

    public PosisiTahapPemberkasanData getPosisiTahapPemberkasanPenerima() {
        return posisiTahapPemberkasanPenerima;
    }

    public void setPosisiTahapPemberkasanPenerima(PosisiTahapPemberkasanData posisiTahapPemberkasanPenerima) {
        this.posisiTahapPemberkasanPenerima = posisiTahapPemberkasanPenerima;
    }

    public StatusFlowJenisLogData getStatusFlow() {
        return statusFlow;
    }

    public void setStatusFlow(StatusFlowJenisLogData statusFlow) {
        this.statusFlow = statusFlow;
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
        if (!(object instanceof RegisterPermohonanData)) {
            return false;
        }
        RegisterPermohonanData other = (RegisterPermohonanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.RegisterPermohonanData[ id=" + id + " ]";
    }

}
