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
@Table(name = "transaksi.tbl_pegawai_perusahaan")
@NamedQueries({
    @NamedQuery(name = "PegawaiPerusahaanData.findAll", query = "SELECT p FROM PegawaiPerusahaanData p"),
    @NamedQuery(name = "PegawaiPerusahaanData.findByPerson", query = "SELECT p FROM PegawaiPerusahaanData p WHERE p.person = :person"),
    @NamedQuery(name = "PegawaiPerusahaanData.findByPerusahaan", query = "SELECT p FROM PegawaiPerusahaanData p WHERE p.perusahaan = :perusahaan"),
    @NamedQuery(name = "PegawaiPerusahaanData.findByJabatan", query = "SELECT p FROM PegawaiPerusahaanData p WHERE p.jabatan = :jabatan"),
    @NamedQuery(name = "PegawaiPerusahaanData.findById", query = "SELECT p FROM PegawaiPerusahaanData p WHERE p.id = :id")})
public class PegawaiPerusahaanData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "person")
    private String person;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "perusahaan")
    private String perusahaan;
    @Size(max = 3)
    @Column(name = "jabatan")
    private String jabatan;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;

    public PegawaiPerusahaanData() {
    }

    public PegawaiPerusahaanData(String id) {
        this.id = id;
    }

    public PegawaiPerusahaanData(String id, String person, String perusahaan) {
        this.id = id;
        this.person = person;
        this.perusahaan = perusahaan;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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
        if (!(object instanceof PegawaiPerusahaanData)) {
            return false;
        }
        PegawaiPerusahaanData other = (PegawaiPerusahaanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.PegawaiPerusahaanData[ id=" + id + " ]";
    }

}
