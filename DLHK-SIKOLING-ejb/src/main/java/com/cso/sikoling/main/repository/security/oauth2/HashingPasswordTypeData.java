package com.cso.sikoling.main.repository.security.oauth2;

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
@Table(name = "oauth.tbl_hashing_password_type")
@NamedQueries({
    @NamedQuery(name = "HashingPasswordTypeData.findAll", query = "SELECT h FROM HashingPasswordTypeData h"),
    @NamedQuery(name = "HashingPasswordTypeData.findById", query = "SELECT h FROM HashingPasswordTypeData h WHERE h.id = :id"),
    @NamedQuery(name = "HashingPasswordTypeData.findByNama", query = "SELECT h FROM HashingPasswordTypeData h WHERE h.nama = :nama"),
    @NamedQuery(name = "HashingPasswordTypeData.updateId", query = "UPDATE HashingPasswordTypeData SET id = :idBaru WHERE id = :idLama")
})
public class HashingPasswordTypeData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "id")    
    private String id;
    
    @Size(max = 255)
    @Column(name = "nama")
    private String nama;

    public HashingPasswordTypeData() {
    }

    public HashingPasswordTypeData(String id) {
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
        if (!(object instanceof HashingPasswordTypeData)) {
            return false;
        }
        HashingPasswordTypeData other = (HashingPasswordTypeData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.oauth2.HashingPasswordTypeData[ id=" + id + " ]";
    }

}
