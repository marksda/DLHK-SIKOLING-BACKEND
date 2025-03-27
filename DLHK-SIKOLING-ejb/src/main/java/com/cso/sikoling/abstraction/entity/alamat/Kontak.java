package com.cso.sikoling.abstraction.entity.alamat;

import java.io.Serializable;
import java.util.Objects;

public class Kontak implements Serializable {
    
	private final String telepone;
	private final String fax;
	private final String email;
	
	public Kontak(String telepone, String fax, String email) {
            this.telepone = telepone;
            this.fax = fax;
            this.email = email;
	}
        
	public String getTelepone() {
            return telepone;
	}

	public String getFax() {
            return fax;
	}

	public String getEmail() {
            return email;
	}
	
        @Override
	public int hashCode() {
            
            int hash = 17;
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

            final Kontak other = (Kontak) obj;

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
