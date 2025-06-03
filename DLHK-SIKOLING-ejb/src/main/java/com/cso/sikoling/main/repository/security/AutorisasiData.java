
package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.main.repository.person.PersonData;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanData;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "master.tbl_autorisasi")
@NamedQueries({
    @NamedQuery(name = "AutorisasiData.findAll", query = "SELECT a FROM AutorisasiData a"),
    @NamedQuery(name = "AutorisasiData.findByIdLama", query = "SELECT a FROM AutorisasiData a WHERE a.idLama = :idLama"),
    @NamedQuery(name = "AutorisasiData.findByStatusInternal", query = "SELECT a FROM AutorisasiData a WHERE a.statusInternal = :statusInternal"),
    @NamedQuery(name = "AutorisasiData.findByIsVerified", query = "SELECT a FROM AutorisasiData a WHERE a.isVerified = :isVerified"),
    @NamedQuery(name = "AutorisasiData.findByUserName", query = "SELECT a FROM AutorisasiData a WHERE a.userName = :userName"),
    @NamedQuery(name = "AutorisasiData.findById", query = "SELECT a FROM AutorisasiData a WHERE a.id = :id"),
    @NamedQuery(name = "AutorisasiData.findByTanggalRegistrasi", query = "SELECT a FROM AutorisasiData a WHERE a.tanggalRegistrasi = :tanggalRegistrasi"),
    @NamedQuery(name = "AutorisasiData.updateId", query = "UPDATE AutorisasiData SET id = :idBaru WHERE id = :idLama")})
public class AutorisasiData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "id_user")
    private String idUser;
    
    @Column(name = "status_internal")
    private Boolean statusInternal;
    
    @Column(name = "is_verified")
    private Boolean isVerified;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user_name")
    private String userName;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;
    
    @OneToMany(mappedBy = "kreator")
    private Collection<PerusahaanData> perusahaanDataCollection;
    
    @OneToMany(mappedBy = "verifikator")
    private Collection<PerusahaanData> perusahaanDataCollection1;
    
    @JoinColumn(name = "hak_akses", referencedColumnName = "id")
    @ManyToOne
    private HakAksesData hakAkses;
    
    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PersonData person;

    public AutorisasiData() {
    }

    public AutorisasiData(String id) {
        this.id = id;
    }

    public AutorisasiData(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Boolean getStatusInternal() {
        return statusInternal;
    }

    public void setStatusInternal(Boolean statusInternal) {
        this.statusInternal = statusInternal;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public void setTanggalRegistrasi(Date tanggalRegistrasi) {
        this.tanggalRegistrasi = tanggalRegistrasi;
    }

    public Collection<PerusahaanData> getPerusahaanDataCollection() {
        return perusahaanDataCollection;
    }

    public void setPerusahaanDataCollection(Collection<PerusahaanData> perusahaanDataCollection) {
        this.perusahaanDataCollection = perusahaanDataCollection;
    }

    public Collection<PerusahaanData> getPerusahaanDataCollection1() {
        return perusahaanDataCollection1;
    }

    public void setPerusahaanDataCollection1(Collection<PerusahaanData> perusahaanDataCollection1) {
        this.perusahaanDataCollection1 = perusahaanDataCollection1;
    }

    public HakAksesData getHakAkses() {
        return hakAkses;
    }

    public void setHakAkses(HakAksesData hakAkses) {
        this.hakAkses = hakAkses;
    }

    public PersonData getPerson() {
        return person;
    }

    public void setPerson(PersonData person) {
        this.person = person;
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
        if (!(object instanceof AutorisasiData)) {
            return false;
        }
        AutorisasiData other = (AutorisasiData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.AutorisasiData[ id=" + id + " ]";
    }

}
