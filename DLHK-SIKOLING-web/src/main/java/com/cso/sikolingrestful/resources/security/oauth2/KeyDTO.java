package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class KeyDTO implements Serializable {
    
    private String id;
    private RealmDTO realm;
    private JwaDTO jwa;
    private EncodingSchemeDTO encoding_scheme;
    private String secred_key;
    private String private_key;
    private String public_key;
    private String tanggal;

    public KeyDTO() {
    }
	
    public KeyDTO(Key t) {
        if(t != null) {
            this.id = t.getId();
            this.realm = t.getRealm() != null ?
                    new RealmDTO(t.getRealm()) : null;
            this.jwa = t.getJwa() != null ? new JwaDTO(t.getJwa()) : null;
            this.encoding_scheme = t.getEncoding_scheme() != null ?
                    new EncodingSchemeDTO(t.getEncoding_scheme()) : null;
            this.secred_key = t.getSecred_key();
            this.private_key = t.getPrivate_key();
            this.public_key = t.getPublic_key();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date tmpTglReg = t.getTanggal();
            this.tanggal = tmpTglReg != null ? df.format(tmpTglReg) : null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmDTO getRealm() {
        return realm;
    }

    public void setRealm(RealmDTO realm) {
        this.realm = realm;
    }

    public JwaDTO getJwa() {
        return jwa;
    }

    public void setJwa(JwaDTO jwa) {
        this.jwa = jwa;
    }

    public EncodingSchemeDTO getEncoding_scheme() {
        return encoding_scheme;
    }

    public void setEncoding_scheme(EncodingSchemeDTO encoding_scheme) {
        this.encoding_scheme = encoding_scheme;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    public Key toKey() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        try {
            Date date = this.tanggal != null ? df.parse(this.tanggal) : null;
            
            return new Key(
                this.id, 
                this.realm != null ? this.realm.toRealm() : null, 
                this.jwa != null ? this.jwa.toJwa() : null,
                this.encoding_scheme != null ? 
                        this.encoding_scheme.toEncodingScheme(): null,
                this.secred_key, 
                this.private_key, 
                this.public_key,
                date
            );
        } catch (ParseException ex) {
            return new Key(
                this.id, 
                this.realm != null ? this.realm.toRealm() : null, 
                this.jwa != null ? this.jwa.toJwa() : null,
                this.encoding_scheme != null ? 
                        this.encoding_scheme.toEncodingScheme(): null,
                this.secred_key, 
                this.private_key, 
                this.public_key,
                null
            );
        }        
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
                .concat("}");
    }
	
}
