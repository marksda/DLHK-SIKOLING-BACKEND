package com.cso.sikoling.main.repository.dokumen;

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
@Table(name = "master.tbl_dokumen")
@NamedQueries({
    @NamedQuery(name = "DokumenData.findAll", query = "SELECT d FROM DokumenData d"),
    @NamedQuery(name = "DokumenData.findById", query = "SELECT d FROM DokumenData d WHERE d.id = :id"),
    @NamedQuery(name = "DokumenData.findByNama", query = "SELECT d FROM DokumenData d WHERE d.nama = :nama"),
    @NamedQuery(name = "DokumenData.findByIdLama", query = "SELECT d FROM DokumenData d WHERE d.idLama = :idLama"),
    @NamedQuery(name = "DokumenData.findBySingkatan", query = "SELECT d FROM DokumenData d WHERE d.singkatan = :singkatan"),
    @NamedQuery(name = "DokumenData.updateId", query = "UPDATE DokumenData SET id = :idBaru WHERE id = :idLama")})
public class DokumenData implements Serializable {

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
    
    @Column(name = "id_lama")
    private Short idLama;
    
    @Size(max = 2147483647)
    @Column(name = "singkatan")
    private String singkatan;

    public DokumenData() {
    }

    public DokumenData(String id) {
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

    public Short getIdLama() {
        return idLama;
    }

    public void setIdLama(Short idLama) {
        this.idLama = idLama;
    }

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
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
        if (!(object instanceof DokumenData)) {
            return false;
        }
        DokumenData other = (DokumenData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.DokumenData[ id=" + id + " ]";
    }

}
