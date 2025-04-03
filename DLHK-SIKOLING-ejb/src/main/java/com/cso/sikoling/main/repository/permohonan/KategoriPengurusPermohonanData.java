package com.cso.sikoling.main.repository.permohonan;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "master.tbl_kategori_pengurus_permohonan")
@NamedQueries({
    @NamedQuery(name = "KategoriPengurusPermohonanData.findAll", query = "SELECT k FROM KategoriPengurusPermohonanData k"),
    @NamedQuery(name = "KategoriPengurusPermohonanData.findById", query = "SELECT k FROM KategoriPengurusPermohonanData k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriPengurusPermohonanData.findByNama", query = "SELECT k FROM KategoriPengurusPermohonanData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KategoriPengurusPermohonanData.updateId", query = "UPDATE KategoriPengurusPermohonanData SET id = :idBaru WHERE id = :idLama")})
public class KategoriPengurusPermohonanData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;

    public KategoriPengurusPermohonanData() {
    }

    public KategoriPengurusPermohonanData(String id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KategoriPengurusPermohonanData)) {
            return false;
        }
        KategoriPengurusPermohonanData other = (KategoriPengurusPermohonanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.KategoriPengurusPermohonanData[ id=" + id + " ]";
    }

}
