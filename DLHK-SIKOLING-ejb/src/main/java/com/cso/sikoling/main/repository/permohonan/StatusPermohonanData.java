package com.cso.sikoling.main.repository.permohonan;

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
@Table(name = "master.tbl_status_permohonan")
@NamedQueries({
    @NamedQuery(name = "StatusPermohonanData.findAll", query = "SELECT s FROM StatusPermohonanData s"),
    @NamedQuery(name = "StatusPermohonanData.findById", query = "SELECT s FROM StatusPermohonanData s WHERE s.id = :id"),
    @NamedQuery(name = "StatusPermohonanData.findByNama", query = "SELECT s FROM StatusPermohonanData s WHERE s.nama = :nama"),
    @NamedQuery(name = "StatusPermohonanData.updateId", query = "UPDATE StatusPermohonanData SET id = :idBaru WHERE id = :idLama")})
public class StatusPermohonanData implements Serializable {

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

    public StatusPermohonanData() {
    }

    public StatusPermohonanData(String id) {
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
        if (!(object instanceof StatusPermohonanData)) {
            return false;
        }
        StatusPermohonanData other = (StatusPermohonanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.StatusPermohonanData[ id=" + id + " ]";
    }

}
