package com.cso.sikoling.abstraction.entity.perusahaan;

import java.io.Serializable;
import java.util.Objects;

public class KategoriSkalaUsaha implements Serializable {
    
	private final String id;	
	private final String nama;
	private final String singkatan;
        
        public KategoriSkalaUsaha(String id, String nama) {
		this.id = id;
		this.nama = nama;
		this.singkatan = null;
	}
	
	public KategoriSkalaUsaha(String id, String nama, String singkatan) {
		this.id = id;
		this.nama = nama;
		this.singkatan = singkatan;
	}

	public String getId() {
		return id;
	}

	public String getNama() {
		return nama;
	}

	public String getSingkatan() {
		return singkatan;
	}
	
        @Override
	public int hashCode() {
            
		int hash = 31;
		hash = 91 * hash + Objects.hashCode(this.id);
		hash = 91 * hash + Objects.hashCode(this.nama);
                
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

            final KategoriSkalaUsaha other = (KategoriSkalaUsaha) obj;

            return this.id.equals(other.getId());
            
	}
	
	@Override
	public String toString() {
            return "SkalaUsaha { id="
                    .concat(this.id)
                    .concat(", nama=")
                    .concat(this.nama)
                    .concat(", singkatan=")
                    .concat(this.singkatan)
                    .concat("}");
	}	
	

}
