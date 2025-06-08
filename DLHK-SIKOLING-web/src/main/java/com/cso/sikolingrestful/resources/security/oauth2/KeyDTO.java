package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class KeyDTO implements Serializable {
    
    private String id;
    private String id_realm;
    private String id_jwa;
    private String id_encoding_scheme;
    private String secred_key;
    private String private_key;
    private String public_key;
    private Date tanggal;

    public KeyDTO() {
    }
	
    public KeyDTO(Key t) {
        if(t != null) {
            this.id = t.getId();
            this.id_realm = t.getId_realm();
            this.id_jwa = t.getId_jwa();
            this.id_encoding_scheme = t.getId_encoding_scheme();
            this.secred_key = t.getSecred_key();
            this.private_key = t.getPrivate_key();
            this.public_key = t.getPublic_key();
            this.tanggal = t.getTanggal();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_realm() {
        return id_realm;
    }

    public void setId_realm(String id_realm) {
        this.id_realm = id_realm;
    }

    public String getId_jwa() {
        return id_jwa;
    }

    public void setId_jwa(String id_jwa) {
        this.id_jwa = id_jwa;
    }

    public String getId_encoding_scheme() {
        return id_encoding_scheme;
    }

    public void setId_encoding_scheme(String id_encoding_scheme) {
        this.id_encoding_scheme = id_encoding_scheme;
    }

    public String getSecred_key() {
        return secred_key;
    }

    public void setSecred_key(String secred_key) {
        this.secred_key = secred_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
    public Key toKey() {
        return new Key(
                this.id, this.id_realm, this.id_jwa,
                this.secred_key, this.private_key, this.public_key,
                this.tanggal
            );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyDTO other = (KeyDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "{Key{ id:"
                .concat(this.id)
                .concat(", id_jwa:")
                .concat(this.id_jwa)
                .concat("}");
    }
	
}
