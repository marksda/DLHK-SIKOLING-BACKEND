
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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_kecamatan")
@NamedQueries({
    @NamedQuery(name = "KecamatanData.findAll", query = "SELECT k FROM KecamatanData k"),
    @NamedQuery(name = "KecamatanData.findById", query = "SELECT k FROM KecamatanData k WHERE k.id = :id"),
    @NamedQuery(name = "KecamatanData.findByNama", query = "SELECT k FROM KecamatanData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KecamatanData.updateId", query = "UPDATE KecamatanData SET id = :idBaru WHERE id = :idLama")})
public class KecamatanData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{7}")
    @Size(min = 1, max = 7)
    @Column(name = "id")
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kecamatan")
    private Collection<DesaData> desaDataCollection;
    
    @JoinColumn(name = "kabupaten", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private KabupatenData kabupaten;

    public KecamatanData() {
    }

    public KecamatanData(String id) {
        this.id = id;
    }

    public KecamatanData(String id, String nama) {
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

    public Collection<DesaData> getDesaDataCollection() {
        return desaDataCollection;
    }

    public void setDesaDataCollection(Collection<DesaData> desaDataCollection) {
        this.desaDataCollection = desaDataCollection;
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
        if (!(object instanceof KecamatanData)) {
            return false;
        }
        KecamatanData other = (KecamatanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.alamat.KecamatanData[ id=" + id + " ]";
    }

}
