package com.cso.sikoling.main.repository.security.oauth2;

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
@Table(name = "oauth.tbl_jwa")
@NamedQueries({
    @NamedQuery(name = "JwaData.findAll", query = "SELECT t FROM JwaData t"),
    @NamedQuery(name = "JwaData.findById", query = "SELECT t FROM JwaData t WHERE t.id = :id"),
    @NamedQuery(name = "JwaData.findByNama", query = "SELECT t FROM JwaData t WHERE t.nama = :nama"),
    @NamedQuery(name = "JwaData.findByKeterangan", query = "SELECT t FROM JwaData t WHERE t.keterangan = :keterangan")})
public class JwaData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;
    
    @Size(max = 255)
    @Column(name = "nama")
    private String nama;
    
    @Size(max = 255)
    @Column(name = "keterangan")
    private String keterangan;
    
    @JoinColumn(name = "jwa_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private JwaTypeData jwa_type;

    public JwaData() {
    }

    public JwaData(String id) {
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

    public JwaTypeData getJwa_type() {
        return jwa_type;
    }

    public void setJwa_type(JwaTypeData jwa_type) {
        this.jwa_type = jwa_type;
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
        if (!(object instanceof JwaData)) {
            return false;
        }
        JwaData other = (JwaData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.oauth2.TblJwa[ id=" + id + " ]";
    }

}
