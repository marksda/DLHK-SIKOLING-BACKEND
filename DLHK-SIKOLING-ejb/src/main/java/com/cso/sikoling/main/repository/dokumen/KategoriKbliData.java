package com.cso.sikoling.main.repository.dokumen;

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
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "master.tbl_kategori_kbli")
@NamedQueries({
    @NamedQuery(name = "KategoriKbliData.findAll", query = "SELECT k FROM KategoriKbliData k"),
    @NamedQuery(name = "KategoriKbliData.findById", query = "SELECT k FROM KategoriKbliData k WHERE k.id = :id"),
    @NamedQuery(name = "KategoriKbliData.findByNama", query = "SELECT k FROM KategoriKbliData k WHERE k.nama = :nama"),
    @NamedQuery(name = "KategoriKbliData.updateId", query = "UPDATE KategoriKbliData SET id = :idBaru WHERE id = :idLama")})
public class KategoriKbliData implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @JoinColumn(name = "versi", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VersiKbliData versiKbli;

    public KategoriKbliData() {
    }

    public KategoriKbliData(String id) {
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

    public VersiKbliData getVersiKbli() {
        return versiKbli;
    }

    public void setVersiKbli(VersiKbliData versiKbli) {
        this.versiKbli = versiKbli;
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
        if (!(object instanceof KategoriKbliData)) {
            return false;
        }
        KategoriKbliData other = (KategoriKbliData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.KategoriKbliData[ id=" + id + " ]";
    }

}
