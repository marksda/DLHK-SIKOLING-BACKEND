
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
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_detail_pelaku_usaha")
@NamedQueries({
    @NamedQuery(name = "DetailPelakuUsahaData.findAll", query = "SELECT d FROM DetailPelakuUsahaData d"),
    @NamedQuery(name = "DetailPelakuUsahaData.findById", query = "SELECT d FROM DetailPelakuUsahaData d WHERE d.id = :id"),
    @NamedQuery(name = "DetailPelakuUsahaData.findByNama", query = "SELECT d FROM DetailPelakuUsahaData d WHERE d.nama = :nama"),
    @NamedQuery(name = "DetailPelakuUsahaData.findBySingkatan", query = "SELECT d FROM DetailPelakuUsahaData d WHERE d.singkatan = :singkatan"),
    @NamedQuery(name = "DetailPelakuUsahaData.updateId", query = "UPDATE DetailPelakuUsahaData SET id = :idBaru WHERE id = :idLama")})
public class DetailPelakuUsahaData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{6}")
    @Size(min = 1, max = 6)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 2147483647)
    @Column(name = "singkatan")
    private String singkatan;
    
    @OneToMany(mappedBy = "pelakuUsaha")
    private Collection<PerusahaanData> perusahaanDataCollection;
    
    @JoinColumn(name = "kategori_pelaku_usaha", referencedColumnName = "id")
    @ManyToOne
    private KategoriPelakuUsahaData kategoriPelakuUsaha;

    public DetailPelakuUsahaData() {
    }

    public DetailPelakuUsahaData(String id) {
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

    public Collection<PerusahaanData> getPerusahaanDataCollection() {
        return perusahaanDataCollection;
    }

    public void setPerusahaanDataCollection(Collection<PerusahaanData> perusahaanDataCollection) {
        this.perusahaanDataCollection = perusahaanDataCollection;
    }

    public KategoriPelakuUsahaData getKategoriPelakuUsaha() {
        return kategoriPelakuUsaha;
    }

    public void setKategoriPelakuUsaha(KategoriPelakuUsahaData kategoriPelakuUsaha) {
        this.kategoriPelakuUsaha = kategoriPelakuUsaha;
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
        if (!(object instanceof DetailPelakuUsahaData)) {
            return false;
        }
        DetailPelakuUsahaData other = (DetailPelakuUsahaData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.DetailPelakuUsahaData[ id=" + id + " ]";
    }

}
