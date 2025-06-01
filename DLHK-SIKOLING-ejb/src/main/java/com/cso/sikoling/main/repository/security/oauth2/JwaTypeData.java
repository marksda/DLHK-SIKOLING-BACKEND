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
@Table(name = "oauth.tbl_jwa_type")
@NamedQueries({
    @NamedQuery(name = "JwaTypeData.findAll", query = "SELECT j FROM JwaTypeData j"),
    @NamedQuery(name = "JwaTypeData.findById", query = "SELECT j FROM JwaTypeData j WHERE j.id = :id"),
    @NamedQuery(name = "JwaTypeData.findByNama", query = "SELECT j FROM JwaTypeData j WHERE j.nama = :nama"),
    @NamedQuery(name = "JwaTypeData.updateId", query = "UPDATE JwaTypeData SET id = :idBaru WHERE id = :idLama")
})
public class JwaTypeData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "id")
    private String id;
    
    @Size(max = 255)
    @Column(name = "nama")
    private String nama;

    public JwaTypeData() {
    }

    public JwaTypeData(String id) {
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
        if (!(object instanceof JwaTypeData)) {
            return false;
        }
        JwaTypeData other = (JwaTypeData) object;
        
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.oauth2.JwaTypeData[ id=" + id + " ]";
    }

}
