package com.cso.sikolingrestful.resources.alamat;

import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import java.util.Objects;

public class KontakDTO {
    
    private String telepone;
    private String fax;
    private String email;

    public KontakDTO() {
    }

    public KontakDTO(Kontak t) {
        
        if( t!= null ) {
            this.telepone = t.getTelepone();
            this.fax = t.getFax();
            this.email = t.getEmail();
        }
    }

    public String getTelepone() {
        return telepone;
    }

    public void setTelepone(String telepone) {
        this.telepone = telepone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Kontak toKontak() {
        return new Kontak(this.telepone, this.fax, this.email);
    }
    
    @Override
    public int hashCode() {

        int hash = 171;
        hash = 71 * hash + Objects.hashCode(this.telepone);
        hash = 71 * hash + Objects.hashCode(this.fax);
        hash = 71 * hash + Objects.hashCode(this.email);

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

        final KontakDTO other = (KontakDTO) obj;

        if (!this.telepone.equals(other.telepone)) {
            return false;
        }

        if (!this.fax.equals(other.fax)) {
            return false;
        }

        return this.email.equals(other.email);
    }
	
    @Override
    public String toString() {
        return "KontakPemrakarsa { telepone="
                .concat(this.telepone)
                .concat(", fax=")
                .concat(this.fax)
                .concat(", email=")
                .concat(this.email)
                .concat("}");
    }

}
