package com.cso.sikoling.main.repository.security;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "master.tbl_user")
@NamedQueries({
    @NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u"),
    @NamedQuery(name = "UserData.findById", query = "SELECT u FROM UserData u WHERE u.id = :id"),
    @NamedQuery(name = "UserData.findByUserName", query = "SELECT u FROM UserData u WHERE u.userName = :userName"),
    @NamedQuery(name = "UserData.findByPassword", query = "SELECT u FROM UserData u WHERE u.password = :password"),
    @NamedQuery(name = "UserData.findByTanggalRegistrasi", query = "SELECT u FROM UserData u WHERE u.tanggalRegistrasi = :tanggalRegistrasi")})
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id")
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user_name")
    private String userName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tanggal_registrasi")
    @Temporal(TemporalType.DATE)
    private Date tanggalRegistrasi;

    public UserData() {
    }

    public UserData(String id) {
        this.id = id;
    }

    public UserData(String id, String userName, String password, Date tanggalRegistrasi) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.tanggalRegistrasi = tanggalRegistrasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }

    public void setTanggalRegistrasi(Date tanggalRegistrasi) {
        this.tanggalRegistrasi = tanggalRegistrasi;
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
        if (!(object instanceof UserData)) {
            return false;
        }
        UserData other = (UserData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikolingrestful.resources.security.UserData[ id=" + id + " ]";
    }

}
