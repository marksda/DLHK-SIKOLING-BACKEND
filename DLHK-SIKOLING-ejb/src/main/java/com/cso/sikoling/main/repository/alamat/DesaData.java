
package com.cso.sikoling.main.repository.alamat;

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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "master.tbl_desa")
@NamedQueries({
    @NamedQuery(name = "DesaData.findAll", query = "SELECT d FROM DesaData d"),
    @NamedQuery(name = "DesaData.findById", query = "SELECT d FROM DesaData d WHERE d.id = :id"),
    @NamedQuery(name = "DesaData.findByNama", query = "SELECT d FROM DesaData d WHERE d.nama = :nama"),
    @NamedQuery(name = "DesaData.updateId", query = "UPDATE DesaData SET id = :idBaru WHERE id = :idLama")})
public class DesaData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{10}")
    @Column(name = "id", length = 10)
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @JoinColumn(name = "propinsi", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PropinsiData propinsi;
    
    @JoinColumn(name = "kabupaten", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private KabupatenData kabupaten;
    
    @JoinColumn(name = "kecamatan", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private KecamatanData kecamatan;

    public DesaData() {
    }

    public DesaData(String id) {
        this.id = id;
    }

    public DesaData(String id, String nama) {
        this.id = id;
        this.nama = nama;
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

    public KabupatenData getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(KabupatenData kabupaten) {
        this.kabupaten = kabupaten;
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
        if (!(object instanceof DesaData)) {
            return false;
        }
        DesaData other = (DesaData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.alamat.DesaData[ id=" + id + " ]";
    }

}
