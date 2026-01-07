package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.main.repository.person.PersonData;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @NamedQuery(name = "PegawaiPerusahaanData.findById", query = "SELECT p FROM PegawaiPerusahaanData p WHERE p.id = :id"),
    @NamedQuery(name = "PegawaiPerusahaanData.updateId", query = "UPDATE PegawaiPerusahaanData SET id = :idBaru WHERE id = :idLama")})
public class PegawaiPerusahaanData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;    
    
    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne
    private PersonData person;
    
    @JoinColumn(name = "perusahaan", referencedColumnName = "id")
    @ManyToOne
    private PerusahaanData perusahaan;
    
    @JoinColumn(name = "jabatan", referencedColumnName = "id")
    @ManyToOne
    private JabatanData jabatan;
    
    @Column(name = "status_aktif")
    private Boolean statusAktif;

    public PegawaiPerusahaanData() {
    }

    public PegawaiPerusahaanData(String id) {
        this.id = id;
    }

    public PegawaiPerusahaanData(String id, PersonData person, PerusahaanData perusahaan) {
        this.id = id;
        this.person = person;
        this.perusahaan = perusahaan;
    }

    public PersonData getPerson() {
        return person;
    }

    public void setPerson(PersonData person) {
        this.person = person;
    }

    public PerusahaanData getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanData perusahaan) {
        this.perusahaan = perusahaan;
    }

    public JabatanData getJabatan() {
        return jabatan;
    }

    public void setJabatan(JabatanData jabatan) {
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
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
        return "cso.sikoling.main.repository.permohonan.PegawaiPerusahaanData[ id=" + id + " ]";
    }

}
