
package com.cso.sikoling.main.repository.perusahaan;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "master.tbl_kategori_skala_usaha")
@NamedQueries({
    @NamedQuery(name = "KategoriSkalaUsahaData.findAll", query = "SELECT k FROM KategoriSkalaUsahaData k"),
    @NamedQuery(name = "KategoriSkalaUsahaData.findById", query = "SELECT k FROM KategoriSkalaUsahaData k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriSkalaUsahaData.findByNama", query = "SELECT k FROM KategoriSkalaUsahaData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KategoriSkalaUsahaData.findBySingkatan", query = "SELECT k FROM KategoriSkalaUsahaData k WHERE k.singkatan = :singkatan"),
    @NamedQuery(name = "KategoriSkalaUsahaData.updateId", query = "UPDATE KategoriSkalaUsahaData SET id = :idBaru WHERE id = :idLama")})
public class KategoriSkalaUsahaData implements Serializable {

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
    @Column(name = "singkatan")
    private String singkatan;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skalaUsaha")
    private Collection<KategoriPelakuUsahaData> kategoriPelakuUsahaDataCollection;
    
    @OneToMany(mappedBy = "skalaUsaha")
    private Collection<PerusahaanData> perusahaanDataCollection;

    public KategoriSkalaUsahaData() {
    }

    public KategoriSkalaUsahaData(String id) {
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

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public Collection<KategoriPelakuUsahaData> getKategoriPelakuUsahaDataCollection() {
        return kategoriPelakuUsahaDataCollection;
    }

    public void setKategoriPelakuUsahaDataCollection(Collection<KategoriPelakuUsahaData> kategoriPelakuUsahaDataCollection) {
        this.kategoriPelakuUsahaDataCollection = kategoriPelakuUsahaDataCollection;
    }

    public Collection<PerusahaanData> getPerusahaanDataCollection() {
        return perusahaanDataCollection;
    }

    public void setPerusahaanDataCollection(Collection<PerusahaanData> perusahaanDataCollection) {
        this.perusahaanDataCollection = perusahaanDataCollection;
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
        if (!(object instanceof KategoriSkalaUsahaData)) {
            return false;
        }
        KategoriSkalaUsahaData other = (KategoriSkalaUsahaData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.KategoriSkalaUsahaData[ id=" + id + " ]";
    }

}
