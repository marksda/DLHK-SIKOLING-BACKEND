package com.cso.sikoling.abstraction.entity.alamat;

import java.io.Serializable;
import java.util.Objects;

public class Alamat implements Serializable {
	
    private final Propinsi propinsi;
    private final Kabupaten kabupaten;
    private final Kecamatan kecamatan;
    private final Desa desa;
    private final String keterangan;

    public Alamat(Propinsi propinsi, Kabupaten kabupaten, Kecamatan kecamatan, Desa desa, String keterangan) {
        this.propinsi = propinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.keterangan = keterangan;
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
        hash = 13 * hash + Objects.hashCode(this.propinsi);
        hash = 13 * hash + Objects.hashCode(this.kabupaten);
        hash = 13 * hash + Objects.hashCode(this.kecamatan);
        hash = 13 * hash + Objects.hashCode(this.desa);
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

        return this.propinsi.getId().equals(other.propinsi.getId()) 
                && this.kabupaten.getId().equals(other.kabupaten.getId()) 
                && this.kecamatan.getId().equals(other.kecamatan.getId())
                && this.desa.getId().equals(other.desa.getId())
                && this.keterangan.equals(other.keterangan);
    }

    @Override
    public String toString() {
        return "Alamat { propinsi="
                .concat(this.propinsi.getNama())
                .concat(", kabupaten=")
                .concat(this.kabupaten.getNama())
                .concat(", kecamatan=")
                .concat(this.kecamatan.getNama())
                .concat(", desa=")
                .concat(this.desa.getNama())
                .concat(", keterangan=")
                .concat(this.keterangan)
                .concat("}");
    }

}
