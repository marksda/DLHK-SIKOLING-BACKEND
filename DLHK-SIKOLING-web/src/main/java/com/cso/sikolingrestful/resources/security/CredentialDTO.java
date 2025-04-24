package com.cso.sikolingrestful.resources.security;

import com.cso.sikoling.abstraction.entity.Credential;
import java.io.Serializable;
import java.util.Objects;


public class CredentialDTO implements Serializable {
    
    private String email;
    private String password;	

    public CredentialDTO() {
    }
	
    public CredentialDTO(Credential t) {
        if(t != null) {
            this.email = t.getEmail();
            this.password = t.getPassword();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Credential toCredential() {
        return new Credential(this.email, this.password);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.password);
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
        final CredentialDTO other = (CredentialDTO) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }
    
    @Override
    public String toString() {
        return "{Credential{ email:"
                .concat(this.email)
                .concat(", password:")
                .concat(this.password)
                .concat("}");
    }
	
}
