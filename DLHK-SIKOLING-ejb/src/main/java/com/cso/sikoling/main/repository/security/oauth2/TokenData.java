package com.cso.sikoling.main.repository.security.oauth2;

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
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name = "master.tbl_token")
@NamedQueries({
    @NamedQuery(name = "TokenData.findAll", query = "SELECT t FROM TokenData t"),
    @NamedQuery(name = "TokenData.findBySessionId", query = "SELECT t FROM TokenData t WHERE t.sessionId = :sessionId"),
    @NamedQuery(name = "TokenData.findByAccessToken", query = "SELECT t FROM TokenData t WHERE t.accessToken = :accessToken"),
    @NamedQuery(name = "TokenData.findByRefreshToken", query = "SELECT t FROM TokenData t WHERE t.refreshToken = :refreshToken"),
    @NamedQuery(name = "TokenData.findByExpiresIn", query = "SELECT t FROM TokenData t WHERE t.expiresIn = :expiresIn"),
    @NamedQuery(name = "TokenData.findByTanggalGenerate", query = "SELECT t FROM TokenData t WHERE t.tanggalGenerate = :tanggalGenerate")})
public class TokenData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "session_id")
    private String sessionId;
    
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

    public TokenData() {
    }

    public TokenData(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {        
        if (!(object instanceof TokenData)) {
            return false;
        }
        
        TokenData other = (TokenData) object;
        
        return !this.sessionId.equals(other.sessionId);
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.TokenData[ sessionId=" + sessionId + " ]";
    }

}
