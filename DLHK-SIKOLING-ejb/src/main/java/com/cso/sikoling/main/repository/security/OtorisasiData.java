package com.cso.sikoling.main.repository.security;

import com.cso.sikoling.main.repository.person.PersonData;
import com.cso.sikoling.main.repository.security.oauth2.RealmData;
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
@Table(name = "master.tbl_otorisasi")
@NamedQueries({
    @NamedQuery(name = "OtorisasiData.findAll", query = "SELECT a FROM OtorisasiData a"),
    @NamedQuery(name = "OtorisasiData.findByIdUser", query = "SELECT a FROM OtorisasiData a WHERE a.idUser = :idUser"),
    @NamedQuery(name = "OtorisasiData.findByIsVerified", query = "SELECT a FROM OtorisasiData a WHERE a.isVerified = :isVerified"),
    @NamedQuery(name = "OtorisasiData.findByUserName", query = "SELECT a FROM OtorisasiData a WHERE a.userName = :userName"),
    @NamedQuery(name = "OtorisasiData.findById", query = "SELECT a FROM OtorisasiData a WHERE a.id = :id"),
    @NamedQuery(name = "OtorisasiData.findByTanggalRegistrasi", query = "SELECT a FROM OtorisasiData a WHERE a.tanggalRegistrasi = :tanggalRegistrasi"),
    @NamedQuery(name = "OtorisasiData.updateId", query = "UPDATE OtorisasiData SET id = :idBaru WHERE id = :idLama")
})
public class OtorisasiData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "id_user")
    private String idUser;
    
    @Column(name = "is_verified")
    private Boolean isVerified;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;
    
//    @OneToMany(mappedBy = "kreator")
//    private Collection<PerusahaanData> perusahaanDataCollection;
    
//    @OneToMany(mappedBy = "verifikator")
//    private Collection<PerusahaanData> perusahaanDataCollection1;
    
    @JoinColumn(name = "hak_akses", referencedColumnName = "id")
    @ManyToOne
    private HakAksesData hakAkses;
    
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PersonData person;
    
    @JoinColumn(name = "realm", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RealmData realm;

    public OtorisasiData() {
    }

    public OtorisasiData(String id) {
        this.id = id;
    }

    public OtorisasiData(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

//    public Collection<PerusahaanData> getPerusahaanDataCollection() {
//        return perusahaanDataCollection;
//    }

//    public void setPerusahaanDataCollection(Collection<PerusahaanData> perusahaanDataCollection) {
//        this.perusahaanDataCollection = perusahaanDataCollection;
//    }

//    public Collection<PerusahaanData> getPerusahaanDataCollection1() {
//        return perusahaanDataCollection1;
//    }

//    public void setPerusahaanDataCollection1(Collection<PerusahaanData> perusahaanDataCollection1) {
//        this.perusahaanDataCollection1 = perusahaanDataCollection1;
//    }

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

    public RealmData getRealm() {
        return realm;
    }

    public void setRealm(RealmData realm) {
        this.realm = realm;
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
        if (!(object instanceof OtorisasiData)) {
            return false;
        }
        OtorisasiData other = (OtorisasiData) object;
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
