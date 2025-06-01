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
@Table(name = "oauth.tbl_realm")
@NamedQueries({
    @NamedQuery(name = "RealmData.findAll", query = "SELECT r FROM RealmData r"),
    @NamedQuery(name = "RealmData.findById", query = "SELECT r FROM RealmData r WHERE r.id = :id"),
    @NamedQuery(name = "RealmData.findByNama", query = "SELECT r FROM RealmData r WHERE r.nama = :nama"),
    @NamedQuery(name = "RealmData.updateId", query = "UPDATE RealmData SET id = :idBaru WHERE id = :idLama")
})
public class RealmData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", length = 36)
    private String id;
    
    @Size(max = 255)
    @Column(name = "nama")
    private String nama;

    public RealmData() {
    }

    public RealmData(String id) {
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
        if (!(object instanceof RealmData)) {
            return false;
        }
        RealmData other = (RealmData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.oauth2.RealmData[ id=" + id + " ]";
    }

}
