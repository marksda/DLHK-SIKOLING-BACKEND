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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name = "oauth.tbl_token")
@NamedQueries({
    @NamedQuery(name = "TokenData.findAll", query = "SELECT t FROM TokenData t"),
    @NamedQuery(name = "TokenData.findById", query = "SELECT t FROM TokenData t WHERE t.id = :id"),
    @NamedQuery(name = "TokenData.findByAccessToken", query = "SELECT t FROM TokenData t WHERE t.accessToken = :accessToken"),
    @NamedQuery(name = "TokenData.findByRefreshToken", query = "SELECT t FROM TokenData t WHERE t.refreshToken = :refreshToken"),
    @NamedQuery(name = "TokenData.findByExpiresIn", query = "SELECT t FROM TokenData t WHERE t.expiresIn = :expiresIn"),
    @NamedQuery(name = "TokenData.findByTanggalGenerate", query = "SELECT t FROM TokenData t WHERE t.tanggalGenerate = :tanggalGenerate"),
    @NamedQuery(name = "TokenData.updateId", query = "UPDATE TokenData SET id = :idBaru WHERE id = :idLama")
})
public class TokenData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    
    @Size(max = 2147483647)
    @Column(name = "access_token")
    private String accessToken;
    
    @Size(max = 2147483647)
    @Column(name = "refresh_token")
    private String refreshToken;
    
    @Column(name = "expires_in")
    private BigInteger expiresIn;
    
    @Column(name = "tanggal_generate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalGenerate;
    
    @Size(max = 2147483647)
    @Column(name = "user_name")
    private String userName;
    
    @JoinColumn(name = "realm", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RealmData realm;

    public TokenData() {
    }

    public TokenData(String sessionId) {
        this.id = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BigInteger getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(BigInteger expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getTanggalGenerate() {
        return tanggalGenerate;
    }

    public void setTanggalGenerate(Date tanggalGenerate) {
        this.tanggalGenerate = tanggalGenerate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RealmData getRealm() {
        return realm;
    }

    public void setRealm(RealmData realm) {
        this.realm = realm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {        
        if (!(object instanceof TokenData)) {
            return false;
        }
        
        TokenData other = (TokenData) object;
        
        return !this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.TokenData[ sessionId=" + id + " ]";
    }

}
