
package com.cso.sikoling.main.repository.perusahaan;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_kategori_pelaku_usaha")
@NamedQueries({
    @NamedQuery(name = "KategoriPelakuUsahaData.findAll", query = "SELECT k FROM KategoriPelakuUsahaData k"),
    @NamedQuery(name = "KategoriPelakuUsahaData.findByNama", query = "SELECT k FROM KategoriPelakuUsahaData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KategoriPelakuUsahaData.findById", query = "SELECT k FROM KategoriPelakuUsahaData k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriPelakuUsahaData.updateId", query = "UPDATE KategoriPelakuUsahaData SET id = :idBaru WHERE id = :idLama")})
public class KategoriPelakuUsahaData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id")
    private String id;
    
    @JoinColumn(name = "skala_usaha", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private KategoriSkalaUsahaData skalaUsaha;
    
    @OneToMany(mappedBy = "kategoriPelakuUsaha")
    private Collection<DetailPelakuUsahaData> detailPelakuUsahaDataCollection;

    public KategoriPelakuUsahaData() {
    }

    public KategoriPelakuUsahaData(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KategoriSkalaUsahaData getSkalaUsaha() {
        return skalaUsaha;
    }

    public void setSkalaUsaha(KategoriSkalaUsahaData skalaUsaha) {
        this.skalaUsaha = skalaUsaha;
    }

    public Collection<DetailPelakuUsahaData> getDetailPelakuUsahaDataCollection() {
        return detailPelakuUsahaDataCollection;
    }

    public void setDetailPelakuUsahaDataCollection(Collection<DetailPelakuUsahaData> detailPelakuUsahaDataCollection) {
        this.detailPelakuUsahaDataCollection = detailPelakuUsahaDataCollection;
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
        if (!(object instanceof KategoriPelakuUsahaData)) {
            return false;
        }
        KategoriPelakuUsahaData other = (KategoriPelakuUsahaData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.KategoriPelakuUsahaData[ id=" + id + " ]";
    }

}
