package com.cso.sikoling.abstraction.entity.security;

import java.io.Serializable;
import java.util.Objects;


public class Token implements Serializable {
    private final String access_token;
    private final String refreshToken;
    private final Long expires_in;
    private final String sessionId;

    public Token(String access_token, String refreshToken, Long expires_in, String sessionId) {
        this.access_token = access_token;
        this.refreshToken = refreshToken;
        this.expires_in = expires_in;
        this.sessionId = sessionId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.sessionId);
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
        return Objects.equals(this.sessionId, other.sessionId);
    }
    
}
