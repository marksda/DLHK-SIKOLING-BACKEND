package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Key implements Serializable {
    private final String id;
    private final String id_realm;
    private final String id_jwa;
    private final String id_encoding_scheme;
    private final String secred_key;
    private final String private_key;
    private final String public_key;
    private final Date tanggal;

    public Key(String id, String id_realm, String id_jwa, String id_encoding_scheme, 
            String secred_key, Date tanggal) {
        this.id = id;
        this.id_realm = id_realm;
        this.id_jwa = id_jwa;
        this.id_encoding_scheme = id_encoding_scheme;
        this.secred_key = secred_key;
        this.private_key = null;
        this.public_key = null;
        this.tanggal = tanggal;
    }
    
    public Key(String id, String id_realm, String id_jwa, String id_encoding_scheme, 
            String private_key, String public_key, Date tanggal) {
        this.id = id;
        this.id_realm = id_realm;
        this.id_jwa = id_jwa;
        this.id_encoding_scheme = id_encoding_scheme;
        this.secred_key = null;
        this.private_key = private_key;
        this.public_key = public_key;
        this.tanggal = tanggal;
    }
    
    public Key(String id, String id_realm, String id_jwa, String id_encoding_scheme, 
            String secred_key, String private_key, String public_key, Date tanggal) {
        this.id = id;
        this.id_realm = id_realm;
        this.id_jwa = id_jwa;
        this.id_encoding_scheme = id_encoding_scheme;
        this.secred_key = secred_key;
        this.private_key = private_key;
        this.public_key = public_key;
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public String getId_realm() {
        return id_realm;
    }

    public String getId_jwa() {
        return id_jwa;
    }

    public String getSecred_key() {
        return secred_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getId_encoding_scheme() {
        return id_encoding_scheme;
    }

    public Date getTanggal() {
        return tanggal;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Key other = (Key) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Key{" + "id=" + this.id + "}";
    }
    
}
