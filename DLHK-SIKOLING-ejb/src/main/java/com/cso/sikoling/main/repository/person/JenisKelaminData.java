
package com.cso.sikoling.main.repository.person;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "master.tbl_jenis_kelamin")
@NamedQueries({
    @NamedQuery(name = "JenisKelaminData.findAll", query = "SELECT d FROM JenisKelaminData d"),
    @NamedQuery(name = "DataJenisKelaminData.findById", query = "SELECT d FROM JenisKelaminData d WHERE d.id = :id"),
    @NamedQuery(name = "JenisKelaminData.findByNama", query = "SELECT d FROM JenisKelaminData d WHERE d.nama = :nama"),
    @NamedQuery(name = "JenisKelaminData.updateId", query = "UPDATE JenisKelaminData SET id = :idBaru WHERE id = :idLama")})
public class JenisKelaminData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @OneToMany(mappedBy = "sex")
    private Collection<PersonData> personDataCollection;

    public JenisKelaminData() {
    }

    public JenisKelaminData(String id) {
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

    public Collection<PersonData> getPersonDataCollection() {
        return personDataCollection;
    }

    public void setPersonDataCollection(Collection<PersonData> personDataCollection) {
        this.personDataCollection = personDataCollection;
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
        if (!(object instanceof JenisKelaminData)) {
            return false;
        }
        JenisKelaminData other = (JenisKelaminData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.DataJenisKelamin[ id=" + id + " ]";
    }

}
