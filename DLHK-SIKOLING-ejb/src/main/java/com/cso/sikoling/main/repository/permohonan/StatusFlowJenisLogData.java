package com.cso.sikoling.main.repository.permohonan;

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
@Table(name = "tbl_status_flow_jenis_log")
@NamedQueries({
    @NamedQuery(name = "StatusFlowJenisLogData.findAll", query = "SELECT s FROM StatusFlowJenisLogData s"),
    @NamedQuery(name = "StatusFlowJenisLogData.findById", query = "SELECT s FROM StatusFlowJenisLogData s WHERE s.id = :id"),
    @NamedQuery(name = "StatusFlowJenisLogData.findByKeterangan", query = "SELECT s FROM StatusFlowJenisLogData s WHERE s.keterangan = :keterangan"),
    @NamedQuery(name = "StatusFlowJenisLogData.updateId", query = "UPDATE StatusFlowJenisLogData SET id = :idBaru WHERE id = :idLama")})
public class StatusFlowJenisLogData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{1}")
    @Size(min = 1, max = 1)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "keterangan")
    private String keterangan;

    public StatusFlowJenisLogData() {
    }

    public StatusFlowJenisLogData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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
        if (!(object instanceof StatusFlowJenisLogData)) {
            return false;
        }
        StatusFlowJenisLogData other = (StatusFlowJenisLogData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.StatusFlowJenisLogData[ id=" + id + " ]";
    }

}
