
package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.main.repository.security.AutorisasiData;
import com.cso.sikoling.main.repository.alamat.DesaData;
import com.cso.sikoling.main.repository.alamat.KabupatenData;
import com.cso.sikoling.main.repository.alamat.KecamatanData;
import com.cso.sikoling.main.repository.alamat.PropinsiData;
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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "master.tbl_perusahaan")
@NamedQueries({
    @NamedQuery(name = "PerusahaanData.findAll", query = "SELECT p FROM PerusahaanData p"),
    @NamedQuery(name = "PerusahaanData.findById", query = "SELECT p FROM PerusahaanData p WHERE p.id = :id"),
    @NamedQuery(name = "PerusahaanData.findByNama", query = "SELECT p FROM PerusahaanData p WHERE p.nama = :nama"),
    @NamedQuery(name = "PerusahaanData.findByDetailAlamat", query = "SELECT p FROM PerusahaanData p WHERE p.detailAlamat = :detailAlamat"),
    @NamedQuery(name = "PerusahaanData.findByStatusVerifikasi", query = "SELECT p FROM PerusahaanData p WHERE p.statusVerifikasi = :statusVerifikasi"),
    @NamedQuery(name = "PerusahaanData.findByTelepone", query = "SELECT p FROM PerusahaanData p WHERE p.telepone = :telepone"),
    @NamedQuery(name = "PerusahaanData.findByFax", query = "SELECT p FROM PerusahaanData p WHERE p.fax = :fax"),
    @NamedQuery(name = "PerusahaanData.findByEmail", query = "SELECT p FROM PerusahaanData p WHERE p.email = :email"),
    @NamedQuery(name = "PerusahaanData.findByTanggalRegistrasi", query = "SELECT p FROM PerusahaanData p WHERE p.tanggalRegistrasi = :tanggalRegistrasi"),
    @NamedQuery(name = "PerusahaanData.findByNpwp", query = "SELECT p FROM PerusahaanData p WHERE p.npwp = :npwp"),
    @NamedQuery(name = "PerusahaanData.findByIdLama", query = "SELECT p FROM PerusahaanData p WHERE p.idLama = :idLama"),
    @NamedQuery(name = "PerusahaanData.updateId", query = "UPDATE PerusahaanData SET id = :idBaru WHERE id = :idLama")})
public class PerusahaanData implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{8}")
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 2147483647)
    @Column(name = "detail_alamat")
    private String detailAlamat;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "status_verifikasi")
    private boolean statusVerifikasi;
    
    @Size(max = 2147483647)
    @Column(name = "telepone")
    private String telepone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    
    @Size(max = 2147483647)
    @Column(name = "fax")
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;
    
    @Size(max = 2147483647)
    @Column(name = "npwp")
    private String npwp;
    
    @Size(max = 2147483647)
    @Column(name = "id_lama")
    private String idLama;
    
    @JoinColumn(name = "kreator", referencedColumnName = "id")
    @ManyToOne
    private AutorisasiData kreator;
    
    @JoinColumn(name = "verifikator", referencedColumnName = "id")
    @ManyToOne
    private AutorisasiData verifikator;
    
    @JoinColumn(name = "desa", referencedColumnName = "id")
    @ManyToOne
    private DesaData desa;
    
    @JoinColumn(name = "pelaku_usaha", referencedColumnName = "id")
    @ManyToOne
    private DetailPelakuUsahaData pelakuUsaha;
    
    @JoinColumn(name = "kabupaten", referencedColumnName = "id")
    @ManyToOne
    private KabupatenData kabupaten;
    
    @JoinColumn(name = "model_perizinan", referencedColumnName = "id")
    @ManyToOne
    private KategoriModelPerizinanData modelPerizinan;
    
    @JoinColumn(name = "skala_usaha", referencedColumnName = "id")
    @ManyToOne
    private KategoriSkalaUsahaData skalaUsaha;
    
    @JoinColumn(name = "kecamatan", referencedColumnName = "id")
    @ManyToOne
    private KecamatanData kecamatan;
    
    @JoinColumn(name = "propinsi", referencedColumnName = "id")
    @ManyToOne
    private PropinsiData propinsi;

    public PerusahaanData() {
    }

    public PerusahaanData(String id) {
        this.id = id;
    }

    public PerusahaanData(String id, boolean statusVerifikasi) {
        this.id = id;
        this.statusVerifikasi = statusVerifikasi;
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

    public String getDetailAlamat() {
        return detailAlamat;
    }

    public void setDetailAlamat(String detailAlamat) {
        this.detailAlamat = detailAlamat;
    }

    public boolean getStatusVerifikasi() {
        return statusVerifikasi;
    }

    public void setStatusVerifikasi(boolean statusVerifikasi) {
        this.statusVerifikasi = statusVerifikasi;
    }

    public String getTelepone() {
        return telepone;
    }

    public void setTelepone(String telepone) {
        this.telepone = telepone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public void setTanggalRegistrasi(Date tanggalRegistrasi) {
        this.tanggalRegistrasi = tanggalRegistrasi;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getIdLama() {
        return idLama;
    }

    public void setIdLama(String idLama) {
        this.idLama = idLama;
    }

    public AutorisasiData getKreator() {
        return kreator;
    }

    public void setKreator(AutorisasiData kreator) {
        this.kreator = kreator;
    }

    public AutorisasiData getVerifikator() {
        return verifikator;
    }

    public void setVerifikator(AutorisasiData verifikator) {
        this.verifikator = verifikator;
    }

    public DesaData getDesa() {
        return desa;
    }

    public void setDesa(DesaData desa) {
        this.desa = desa;
    }

    public DetailPelakuUsahaData getPelakuUsaha() {
        return pelakuUsaha;
    }

    public void setPelakuUsaha(DetailPelakuUsahaData pelakuUsaha) {
        this.pelakuUsaha = pelakuUsaha;
    }

    public KabupatenData getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(KabupatenData kabupaten) {
        this.kabupaten = kabupaten;
    }

    public KategoriModelPerizinanData getModelPerizinan() {
        return modelPerizinan;
    }

    public void setModelPerizinan(KategoriModelPerizinanData modelPerizinan) {
        this.modelPerizinan = modelPerizinan;
    }

    public KategoriSkalaUsahaData getSkalaUsaha() {
        return skalaUsaha;
    }

    public void setSkalaUsaha(KategoriSkalaUsahaData skalaUsaha) {
        this.skalaUsaha = skalaUsaha;
    }

    public KecamatanData getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(KecamatanData kecamatan) {
        this.kecamatan = kecamatan;
    }

    public PropinsiData getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(PropinsiData propinsi) {
        this.propinsi = propinsi;
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
        if (!(object instanceof PerusahaanData)) {
            return false;
        }
        PerusahaanData other = (PerusahaanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.PerusahaanData[ id=" + id + " ]";
    }

}
