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
@Table(name = "master.tbl_posisi_tahap_pemberkasan")
@NamedQueries({
    @NamedQuery(name = "PosisiTahapPemberkasanData.findAll", query = "SELECT p FROM PosisiTahapPemberkasanData p"),
    @NamedQuery(name = "PosisiTahapPemberkasanData.findById", query = "SELECT p FROM PosisiTahapPemberkasanData p WHERE p.id = :id"),
    @NamedQuery(name = "PosisiTahapPemberkasanData.findByNama", query = "SELECT p FROM PosisiTahapPemberkasanData p WHERE p.nama = :nama"),
    @NamedQuery(name = "PosisiTahapPemberkasanData.findByKeterangan", query = "SELECT p FROM PosisiTahapPemberkasanData p WHERE p.keterangan = :keterangan"),
    @NamedQuery(name = "PosisiTahapPemberkasanData.updateId", query = "UPDATE PosisiTahapPemberkasanData SET id = :idBaru WHERE id = :idLama")})
public class PosisiTahapPemberkasanData implements Serializable {
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp="[\\d]{1}")
    @Size(min = 1, max = 1)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 2147483647)
    @Column(name = "keterangan")
    private String keterangan;

    public PosisiTahapPemberkasanData() {
    }

    public PosisiTahapPemberkasanData(String id) {
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
        if (!(object instanceof PosisiTahapPemberkasanData)) {
            return false;
        }
        PosisiTahapPemberkasanData other = (PosisiTahapPemberkasanData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.permohonan.PosisiTahapPemberkasanData[ id=" + id + " ]";
    }

}
