package com.cso.sikoling.abstraction.entity.security;

import java.io.Serializable;
import java.util.Objects;


public class Token implements Serializable {
    private final String access_token;
    private final String refresh_token;
    private final Long expires_in;
    private final String session_id;

    public Token(String access_token, String refresh_token, Long expires_in, String session_id) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.session_id = session_id;
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

    public String getSession_id() {
        return session_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.session_id);
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
        return Objects.equals(this.session_id, other.session_id);
    }
    
}
