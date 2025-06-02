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
import java.util.Date;


@Entity
@Table(name = "oauth.tbl_key")
@NamedQueries({
    @NamedQuery(name = "KeyData.findAll", query = "SELECT k FROM KeyData k"),
    @NamedQuery(name = "KeyData.findById", query = "SELECT k FROM KeyData k WHERE k.id = :id"),
    @NamedQuery(name = "KeyData.findBySecretKey", query = "SELECT k FROM KeyData k WHERE k.secretKey = :secretKey"),
    @NamedQuery(name = "KeyData.findByPrivateKey", query = "SELECT k FROM KeyData k WHERE k.privateKey = :privateKey"),
    @NamedQuery(name = "KeyData.findByPublicKey", query = "SELECT k FROM KeyData k WHERE k.publicKey = :publicKey"),
    @NamedQuery(name = "KeyData.updateId", query = "UPDATE KeyData SET id = :idBaru WHERE id = :idLama")
})
public class KeyData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    
    @JoinColumn(name = "realm", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RealmData realm;
    
    @JoinColumn(name = "jwa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private JwaData jwa;
    
    @JoinColumn(name = "encoding_scheme", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EncodingSchemaData encoding_scheme;
    
    @Size(max = 2147483647)
    @Column(name = "secret_key")
    private String secretKey;
    
    @Size(max = 2147483647)
    @Column(name = "private_key")
    private String privateKey;
    
    @Size(max = 2147483647)
    @Column(name = "public_key")
    private String publicKey;
    
    @Column(name = "tanggal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;

    public KeyData() {
    }

    public KeyData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmData getRealm() {
        return realm;
    }

    public void setRealm(RealmData realm) {
        this.realm = realm;
    }

    public JwaData getJwa() {
        return jwa;
    }

    public void setJwa(JwaData jwa) {
        this.jwa = jwa;
    }    

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public EncodingSchemaData getEncoding_scheme() {
        return encoding_scheme;
    }

    public void setEncoding_scheme(EncodingSchemaData encoding_scheme) {
        this.encoding_scheme = encoding_scheme;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
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
        if (!(object instanceof KeyData)) {
            return false;
        }
        KeyData other = (KeyData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cso.sikoling.main.repository.security.oauth2.KeyData[ id=" + id + " ]";
    }

}
