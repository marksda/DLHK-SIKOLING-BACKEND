package com.cso.sikoling.abstraction.entity;

import java.io.Serializable;
import java.util.Objects;

public class Credential implements Serializable {
    
    private final String email;
    private final String password;

    public Credential(String userName, String password) {
        this.email = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
	
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 13 * hash + Objects.hashCode(this.email);
        hash = 13 * hash + Objects.hashCode(this.password);
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

        final Credential other = (Credential) obj;

        if (!this.email.equals(other.email)) {
            return false;
        }

        return this.password.equals(other.password);
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
