package com.cso.sikoling.abstraction.entity.alamat;

import java.io.Serializable;
import java.util.Objects;

public class Alamat implements Serializable {

	private static final long serialVersionUID = 8115464344624994983L;
	
	private final Propinsi propinsi;
	private final Kabupaten kabupaten;
	private final Kecamatan kecamatan;
	private final Desa desa;
	private final String keterangan;
	
	public Alamat(Propinsi propinsi, Kabupaten kabupaten, Kecamatan kecamatan, Desa desa, String keterangan) {
		super();
		this.propinsi = propinsi;
		this.kabupaten = kabupaten;
		this.kecamatan = kecamatan;
		this.desa = desa;
		this.keterangan = keterangan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Propinsi getPropinsi() {
		return propinsi;
	}

	public Kabupaten getKabupaten() {
		return kabupaten;
	}

	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	public Desa getDesa() {
		return desa;
	}

	public String getKeterangan() {
		return keterangan;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + Objects.hashCode(this.propinsi.getNama());
		hash = 13 * hash + Objects.hashCode(this.kabupaten.getNama());
		hash = 13 * hash + Objects.hashCode(this.kecamatan.getNama());
		hash = 13 * hash + Objects.hashCode(this.desa.getNama());
		hash = 13 * hash + Objects.hashCode(this.keterangan);
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
        
        final Alamat other = (Alamat) obj;
        
        if (!this.propinsi.equals(other.propinsi)) {
            return false;
        }
        
        if (!this.kabupaten.equals(other.kabupaten)) {
            return false;
        }
        
        if (!this.kecamatan.equals(other.kecamatan)) {
            return false;
        }
        
        if (!this.desa.equals(other.desa)) {
            return false;
        }
        
        if (!this.keterangan.equals(other.keterangan)) {
            return false;
        }
        
        return true;
	}

	@Override
	public String toString() {
		return "Alamat{" + "propinsi=" + propinsi.getNama() + "kabupaten=" + kabupaten.getNama() 
				+ "kecamatan=" + kecamatan.getNama() + "desa=" + desa.getNama() + ", keterangan=" + keterangan + "}";
	}

}
