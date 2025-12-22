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
@Table(name = "master.tbl_status_dokumen")
@NamedQueries({
    @NamedQuery(name = "StatusDokumenData.findAll", query = "SELECT s FROM StatusDokumenData s"),
    @NamedQuery(name = "StatusDokumenData.findById", query = "SELECT s FROM StatusDokumenData s WHERE s.id = :id"),
    @NamedQuery(name = "StatusDokumenData.findByNama", query = "SELECT s FROM StatusDokumenData s WHERE s.nama = :nama"),
    @NamedQuery(name = "StatusDokumenData.updateId", query = "UPDATE StatusDokumenData SET id = :idBaru WHERE id = :idLama")})
public class StatusDokumenData implements Serializable {

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

    public StatusDokumenData() {
    }

    public StatusDokumenData(String id) {
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
        if (!(object instanceof StatusDokumenData)) {
            return false;
        }
        StatusDokumenData other = (StatusDokumenData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.dokumen.StatusDokumenData[ id=" + id + " ]";
    }

}
