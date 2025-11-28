package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Key implements Serializable {
    private final String id;
    private final Realm realm;
    private final Jwa jwa;
    private final EncodingScheme encoding_scheme;
    private final String secred_key;
    private final String private_key;
    private final String public_key;
    private final Date tanggal;

    public Key(String id, Realm realm, Jwa jwa, EncodingScheme encoding_scheme, 
            String secred_key, Date tanggal) {
        this.id = id;
        this.realm = realm;
        this.jwa = jwa;
        this.encoding_scheme = encoding_scheme;
        this.secred_key = secred_key;
        this.private_key = null;
        this.public_key = null;
        this.tanggal = tanggal;
    }
    
    public Key(String id, Realm realm, Jwa jwa, EncodingScheme encoding_scheme, 
            String private_key, String public_key, Date tanggal) {
        this.id = id;
        this.realm = realm;
        this.jwa = jwa;
        this.encoding_scheme = encoding_scheme;
        this.secred_key = null;
        this.private_key = private_key;
        this.public_key = public_key;
        this.tanggal = tanggal;
    }
    
    public Key(String id, Realm realm, Jwa jwa, EncodingScheme encoding_scheme, 
            String secred_key, String private_key, String public_key, Date tanggal) {
        this.id = id;
        this.realm = realm;
        this.jwa = jwa;
        this.encoding_scheme = encoding_scheme;
        this.secred_key = secred_key;
        this.private_key = private_key;
        this.public_key = public_key;
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public Realm getRealm() {
        return realm;
    }

    public Jwa getJwa() {
        return jwa;
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

    public EncodingScheme getEncoding_scheme() {
        return encoding_scheme;
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
