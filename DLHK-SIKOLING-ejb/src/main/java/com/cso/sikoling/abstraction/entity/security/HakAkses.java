package com.cso.sikoling.abstraction.entity.security;

import java.io.Serializable;
import java.util.Objects;

public class HakAkses implements Serializable {

	private static final long serialVersionUID = -8369724257346000023L;
	private final String id;
	private final String nama;
	private final String keterangan;
	
	public HakAkses(String id, String nama, String keterangan) {
		super();
		this.id = id;
		this.nama = nama;
		this.keterangan = keterangan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public String getNama() {
		return nama;
	}

	public String getKeterangan() {
		return keterangan;
	}
	
	public int hashCode() {
		int hash = 13;
		hash = 171 * hash + Objects.hashCode(this.id);
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
        
        final HakAkses other = (HakAkses) obj;
        
        if (!this.id.equals(other.getId())) {
            return false;
        }
        
        return true;
	}
	
	@Override
	public String toString() {
		return "HakAkses {"
				.concat("id=")
				.concat(this.id)
				.concat(", nama=")
				.concat(this.nama)
				.concat(", keterangan=")
				.concat(this.keterangan)
				.concat("}");
	}	

}
