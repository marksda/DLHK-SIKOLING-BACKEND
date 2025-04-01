
package com.cso.sikoling.main.repository.security;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_hak_akses")
@NamedQueries({
    @NamedQuery(name = "HakAksesData.findAll", query = "SELECT h FROM HakAksesData h"),
    @NamedQuery(name = "HakAksesData.findById", query = "SELECT h FROM HakAksesData h WHERE h.id = :id"),
    @NamedQuery(name = "HakAksesData.findByNama", query = "SELECT h FROM HakAksesData h WHERE h.nama = :nama"),
    @NamedQuery(name = "HakAksesData.findByKeterangan", query = "SELECT h FROM HakAksesData h WHERE h.keterangan = :keterangan"),
    @NamedQuery(name = "HakAksesData.updateId", query = "UPDATE HakAksesData SET id = :idBaru WHERE id = :idLama")})
public class HakAksesData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{2}")
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 2147483647)
    @Column(name = "keterangan")
    private String keterangan;
    
    @OneToMany(mappedBy = "hakAkses")
    private Collection<AutorisasiData> autorisasiDataCollection;

    public HakAksesData() {
    }

    public HakAksesData(String id) {
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Collection<AutorisasiData> getAutorisasiDataCollection() {
        return autorisasiDataCollection;
    }

    public void setAutorisasiDataCollection(Collection<AutorisasiData> autorisasiDataCollection) {
        this.autorisasiDataCollection = autorisasiDataCollection;
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
        if (!(object instanceof HakAksesData)) {
            return false;
        }
        HakAksesData other = (HakAksesData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.HakAksesData[ id=" + id + " ]";
    }

}
