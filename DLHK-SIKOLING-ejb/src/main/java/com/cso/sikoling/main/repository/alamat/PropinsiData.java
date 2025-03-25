package com.cso.sikoling.main.repository.alamat;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "master.tbl_propinsi")
@NamedQueries({
    @NamedQuery(name = "PropinsiData.findAll", query = "SELECT p FROM PropinsiData p"),
    @NamedQuery(name = "PropinsiData.findById", query = "SELECT p FROM PropinsiData p WHERE p.id = :id"),
    @NamedQuery(name = "PropinsiData.findByNama", query = "SELECT p FROM PropinsiData p WHERE p.nama = :nama"),
    @NamedQuery(name = "PropinsiData.updateId", query = "UPDATE PropinsiData SET id = :idBaru WHERE id = :idLama")})
public class PropinsiData implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propinsi")
    private Collection<KabupatenData> kabupatenDataCollection;
    
    @Id
    @Basic(optional = false)
    @NotNull    
    @Pattern(regexp="[\\d]{2}")
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;

    public PropinsiData() {
    }

    public PropinsiData(String id) {
        this.id = id;
    }

    public PropinsiData(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(object instanceof PropinsiData)) {
            return false;
        }
        PropinsiData other = (PropinsiData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikolingejb.PropinsiData[ id=" + id + " ]";
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Collection<KabupatenData> getKabupatenDataCollection() {
        return kabupatenDataCollection;
    }

    public void setKabupatenDataCollection(Collection<KabupatenData> kabupatenDataCollection) {
        this.kabupatenDataCollection = kabupatenDataCollection;
    }
    
}
