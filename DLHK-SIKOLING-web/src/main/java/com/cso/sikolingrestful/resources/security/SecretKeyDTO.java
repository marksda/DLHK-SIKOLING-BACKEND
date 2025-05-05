package com.cso.sikolingrestful.resources.security;

import java.io.Serializable;
import java.util.Objects;


public class SecretKeyDTO implements Serializable {
    
    private String secret_key;

    public SecretKeyDTO() {
    }
	
    public SecretKeyDTO(String t) {
        if(t != null) {
            this.secret_key = t;
        }
    }

    public String getSecret_key() {
        return secret_key;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.secret_key);
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
        final SecretKeyDTO other = (SecretKeyDTO) obj;
        return Objects.equals(this.secret_key, other.secret_key);
    }
    
    @Override
    public String toString() {
        return "{SecretKey{ secret_key:"
                .concat(this.secret_key)
                .concat("}");
    }
	
}
