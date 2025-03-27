package com.cso.sikolingrestful.resources.alamat;

import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import java.util.Objects;


public class AlamatDTO {
    
    private PropinsiDTO propinsi;
    private KabupatenDTO kabupaten;
    private KecamatanDTO kecamatan;
    private DesaDTO desa;
    private String keterangan;

    public AlamatDTO() {
    }
    
    public AlamatDTO(Alamat t) {
        
        if(t != null) {
            this.propinsi = t.getPropinsi() != null ? new PropinsiDTO(t.getPropinsi()) : null;
            this.kabupaten = t.getKabupaten() != null ? new KabupatenDTO(t.getKabupaten()) : null;
            this.kecamatan = t.getKecamatan() != null ? new KecamatanDTO(t.getKecamatan()) : null;
            this.desa = t.getDesa() != null ? new DesaDTO(t.getDesa()) : null;
            this.keterangan = t.getKeterangan();
        }
        
    }

    public PropinsiDTO getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(PropinsiDTO propinsi) {
        this.propinsi = propinsi;
    }

    public KabupatenDTO getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(KabupatenDTO kabupaten) {
        this.kabupaten = kabupaten;
    }

    public KecamatanDTO getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(KecamatanDTO kecamatan) {
        this.kecamatan = kecamatan;
    }

    public DesaDTO getDesa() {
        return desa;
    }

    public void setDesa(DesaDTO desa) {
        this.desa = desa;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public Alamat toAlamat() {
        if( this.propinsi == null || this.kabupaten == null 
            || this.kecamatan == null || this.desa == null) {
            throw new IllegalArgumentException("format data json desa tidak sesuai");
        }
        else {
            return new Alamat(
                this.propinsi.toPropinsi(), 
                this.kabupaten.toKabupaten(), 
                this.kecamatan.toKecamatan(), 
                this.desa.toDesa(), 
                keterangan
            );
        }
    }
    
    @Override
    public int hashCode() {
        
        int hash = 71;
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

        final AlamatDTO other = (AlamatDTO) obj;

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
