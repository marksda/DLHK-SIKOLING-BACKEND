package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class TokenDTO implements Serializable {
    
    private String id;		
    private String access_token;
    private String refreshToken;
    private Long expires_in;
    private Date tanggal_generate;

    public TokenDTO() {
    }
	
    public TokenDTO(Token t) {
        if(t != null) {
            this.access_token = t.getAccess_token();
            this.refreshToken = t.getRefresh_token();
            this.expires_in = t.getExpires_in();
            this.id = t.getId();
            this.tanggal_generate = t.getTanggal_generate();
        }
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTanggal_generate() {
        return tanggal_generate;
    }

    public void setTanggal_generate(Date tanggal_generate) {
        this.tanggal_generate = tanggal_generate;
    }
    
    
    public Token toToken() {
        return new Token(
                this.id,
                this.access_token, 
                this.refreshToken, 
                this.expires_in,
                this.tanggal_generate
            );
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final TokenDTO other = (TokenDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
	
}
