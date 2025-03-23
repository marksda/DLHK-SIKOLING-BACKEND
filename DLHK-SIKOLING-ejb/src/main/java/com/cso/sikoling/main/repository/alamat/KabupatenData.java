
package com.cso.sikoling.main.repository.alamat;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_kabupaten")
@NamedQueries({
    @NamedQuery(name = "KabupatenData.findAll", query = "SELECT k FROM KabupatenData k"),
    @NamedQuery(name = "KabupatenData.findById", query = "SELECT k FROM KabupatenData k WHERE k.id = :id"),
    @NamedQuery(name = "KabupatenData.findByNama", query = "SELECT k FROM KabupatenData k WHERE k.nama = :nama")})
public class KabupatenData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id")
    private String id;
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    @JoinColumn(name = "propinsi", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PropinsiData propinsi;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kabupaten")
    private Collection<KecamatanData> kecamatanDataCollection;

    public KabupatenData() {
    }

    public KabupatenData(String id) {
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

    public PropinsiData getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(PropinsiData propinsi) {
        this.propinsi = propinsi;
    }

    public Collection<KecamatanData> getKecamatanDataCollection() {
        return kecamatanDataCollection;
    }

    public void setKecamatanDataCollection(Collection<KecamatanData> kecamatanDataCollection) {
        this.kecamatanDataCollection = kecamatanDataCollection;
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
        if (!(object instanceof KabupatenData)) {
            return false;
        }
        KabupatenData other = (KabupatenData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.alamat.KabupatenData[ id=" + id + " ]";
    }

}
