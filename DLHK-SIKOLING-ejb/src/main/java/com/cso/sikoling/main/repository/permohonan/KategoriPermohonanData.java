package com.cso.sikoling.main.repository.permohonan;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "master.tbl_kategori_permohonan")
@NamedQueries({
    @NamedQuery(name = "KategoriPermohonanData.findAll", query = "SELECT k FROM KategoriPermohonanData k"),
    @NamedQuery(name = "KategoriPermohonanData.findById", query = "SELECT k FROM KategoriPermohonanData k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriPermohonanData.findByNama", query = "SELECT k FROM KategoriPermohonanData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KategoriPermohonanData.findByIdLama", query = "SELECT k FROM KategoriPermohonanData k WHERE k.idLama = :idLama"),
    @NamedQuery(name = "KategoriPermohonanData.updateId", query = "UPDATE KategoriPermohonanData SET id = :idBaru WHERE id = :idLama")})
public class KategoriPermohonanData implements Serializable {

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
    
    @Size(max = 3)
    @Column(name = "id_lama")
    private String idLama;

    public KategoriPermohonanData() {
    }

    public KategoriPermohonanData(String id) {
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

    public String getIdLama() {
        return idLama;
    }

    public void setIdLama(String idLama) {
        this.idLama = idLama;
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
        if (!(object instanceof KategoriPermohonanData)) {
            return false;
        }
        KategoriPermohonanData other = (KategoriPermohonanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.KategoriPermohonanData[ id=" + id + " ]";
    }

}
