package com.cso.sikolingrestful.resources.alamat;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.alamat.Desa;

public class DesaDTO {
    
    private String id;
    private String nama;
    private String id_kecamatan;

    public DesaDTO() {
    }

    public DesaDTO(String id, String nama, String id_kecamatan) {
        this.id = id;
        this.nama = nama;
        this.id_kecamatan = id_kecamatan;
    }
    
    public DesaDTO(Desa t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.id_kecamatan = t.getId_kecamatan();
        }		
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_kecamatan() {
        return id_kecamatan;
    }

    public void setId_kecamatan(String id_kecamatan) {
        this.id_kecamatan = id_kecamatan;
    }
    
    public Desa toDesa() {
        if( this.id == null || this.nama == null) {
            throw new IllegalArgumentException("format data json desa tidak sesuai");
        }
        else {
            return new Desa(this.id, this.nama, this.id_kecamatan);
        }
    }
    
    @Override
    public int hashCode() {
        
        int hash = 753;
        hash = 73 * hash + Objects.hashCode(this.id );
        hash = 73 * hash + Objects.hashCode(this.nama);
        hash = 73 * hash + Objects.hashCode(this.id_kecamatan);
        
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

        final DesaDTO other = (DesaDTO) obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        
        return !(this.id_kecamatan == null ? other.id_kecamatan != null : !this.id_kecamatan.equals(other.id_kecamatan));
        
    }

    @Override
    public String toString() {
        
        if(id == null) {
            return null;
        }			

        return "DesaDTO{" + "id=" + this.id + ", id_kecamatan=" + this.id_kecamatan + ", nama=" + this.nama + "}";	
        
    }

}
