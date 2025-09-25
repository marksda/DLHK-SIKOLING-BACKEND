
package com.cso.sikoling.main.repository.person;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "master.tbl_person")
@NamedQueries({
    @NamedQuery(name = "PersonData.findAll", query = "SELECT p FROM PersonData p"),
    @NamedQuery(name = "PersonData.findById", query = "SELECT p FROM PersonData p WHERE p.id = :id"),
    @NamedQuery(name = "PersonData.findByNama", query = "SELECT p FROM PersonData p WHERE p.nama = :nama"),
    @NamedQuery(name = "PersonData.findByTelepone", query = "SELECT p FROM PersonData p WHERE p.telepone = :telepone"),
    @NamedQuery(name = "PersonData.findByDetailAlamat", query = "SELECT p FROM PersonData p WHERE p.detailAlamat = :detailAlamat"),
    @NamedQuery(name = "PersonData.findByScanKtp", query = "SELECT p FROM PersonData p WHERE p.scanKtp = :scanKtp"),
    @NamedQuery(name = "PersonData.findByEmail", query = "SELECT p FROM PersonData p WHERE p.email = :email"),
    @NamedQuery(name = "PersonData.findByIsValidated", query = "SELECT p FROM PersonData p WHERE p.isValidated = :isValidated"),
    @NamedQuery(name = "PersonData.updateId", query = "UPDATE PersonData SET id = :idBaru WHERE id = :idLama")})
public class PersonData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 2147483647)
    @Column(name = "telepone")
    private String telepone;
    
    @Size(max = 2147483647)
    @Column(name = "detail_alamat")
    private String detailAlamat;
    
    @Size(max = 2147483647)
    @Column(name = "scan_ktp")
    private String scanKtp;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    
    @Column(name = "is_validated")
    private Boolean isValidated;
    
    @JoinColumn(name = "desa", referencedColumnName = "id")
    @ManyToOne
    private DesaData desa;
    
    @JoinColumn(name = "sex", referencedColumnName = "id")
    @ManyToOne
    private JenisKelaminData sex;
    
    @JoinColumn(name = "kecamatan", referencedColumnName = "id")
    @ManyToOne
    private KecamatanData kecamatan;
    
    @JoinColumn(name = "kabupaten", referencedColumnName = "id")
    @ManyToOne
    private KabupatenData kabupaten;
    
    @JoinColumn(name = "propinsi", referencedColumnName = "id")
    @ManyToOne
    private PropinsiData propinsi;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
//    private Collection<AutorisasiData> autorisasiDataCollection;

    public PersonData() {
    }

    public PersonData(String id) {
        this.id = id;
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

    public String getTelepone() {
        return telepone;
    }

    public void setTelepone(String telepone) {
        this.telepone = telepone;
    }

    public KabupatenData getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(KabupatenData kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getDetailAlamat() {
        return detailAlamat;
    }

    public void setDetailAlamat(String detailAlamat) {
        this.detailAlamat = detailAlamat;
    }

    public String getScanKtp() {
        return scanKtp;
    }

    public void setScanKtp(String scanKtp) {
        this.scanKtp = scanKtp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public DesaData getDesa() {
        return desa;
    }

    public void setDesa(DesaData desa) {
        this.desa = desa;
    }

    public JenisKelaminData getSex() {
        return sex;
    }

    public void setSex(JenisKelaminData sex) {
        this.sex = sex;
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

//    public Collection<AutorisasiData> getAutorisasiDataCollection() {
//        return autorisasiDataCollection;
//    }
//
//    public void setAutorisasiDataCollection(Collection<AutorisasiData> autorisasiDataCollection) {
//        this.autorisasiDataCollection = autorisasiDataCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonData)) {
            return false;
        }
        PersonData other = (PersonData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.PersonData[ id=" + id + " ]";
    }

}
