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
@Table(name = "master.tbl_versi_kbli")
@NamedQueries({
    @NamedQuery(name = "VersiKbliData.findAll", query = "SELECT v FROM VersiKbliData v"),
    @NamedQuery(name = "VersiKbliData.findById", query = "SELECT v FROM VersiKbliData v WHERE v.id = :id"),
    @NamedQuery(name = "VersiKbliData.findByNama", query = "SELECT v FROM VersiKbliData v WHERE v.nama = :nama"),
    @NamedQuery(name = "VersiKbliData.updateId", query = "UPDATE VersiKbliData SET id = :idBaru WHERE id = :idLama")})
public class VersiKbliData implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;
    
    @Size(max = 225)
    @Column(name = "nama")
    private String nama;

    public VersiKbliData() {
    }

    public VersiKbliData(String id) {
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
        if (!(object instanceof VersiKbliData)) {
            return false;
        }
        VersiKbliData other = (VersiKbliData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.VersiKbliData[ id=" + id + " ]";
    }

}
