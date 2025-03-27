package com.cso.sikoling.abstraction.entity.alamat;

import java.io.Serializable;
import java.util.Objects;

public class Kontak implements Serializable {

	private static final long serialVersionUID = 1637431148153802397L;
	private final String telepone;
	private final String fax;
	private final String email;
	
	public Kontak(String telepone, String fax, String email) {
		super();
		this.telepone = telepone;
		this.fax = fax;
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
        
        if (!this.email.equals(other.email)) {
            return false;
        }
        
        return true;
	}
	
	@Override
	public String toString() {
		return "KontakPemrakarsa{" + "telepone=" + telepone + ", fax=" + fax  + ", email=" + email + "}";
	}


}
