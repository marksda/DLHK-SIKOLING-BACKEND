package com.cso.sikoling.main.repository.perusahaan;

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
@Table(name = "master.tbl_jabatan")
@NamedQueries({
    @NamedQuery(name = "JabatanData.findAll", query = "SELECT t FROM JabatanData t"),
    @NamedQuery(name = "JabatanData.findById", query = "SELECT t FROM JabatanData t WHERE t.id = :id"),
    @NamedQuery(name = "JabatanData.findByNama", query = "SELECT t FROM JabatanData t WHERE t.nama = :nama"),
    @NamedQuery(name = "JabatanData.updateId", query = "UPDATE JabatanData SET id = :idBaru WHERE id = :idLama")})
public class JabatanData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{3}")
    @Size(min = 1, max = 3)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;

    public JabatanData() {
    }

    public JabatanData(String id) {
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
        if (!(object instanceof JabatanData)) {
            return false;
        }
        JabatanData other = (JabatanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.perusahaan.TblJabatan[ id=" + id + " ]";
    }

}
