package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Token implements Serializable {
    private final String id;
    private final String access_token;
    private final String refresh_token;
    private final Long expires_in;
    private final Date tanggal_generate;
    private final String userName;
    private final Realm realm;

    public Token(String id, String access_token, String refresh_token, 
            Long expires_in, Date tanggal_generate, String userName, Realm realm) {
        this.id = id;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.tanggal_generate = tanggal_generate;
        this.userName = userName;
        this.realm = realm;
    }

    public String getId() {
        return id;
    }    

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public Date getTanggal_generate() {
        return tanggal_generate;
    }

    public String getUserName() {
        return userName;
    }

    public Realm getRealm() {
        return realm;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Token other = (Token) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
