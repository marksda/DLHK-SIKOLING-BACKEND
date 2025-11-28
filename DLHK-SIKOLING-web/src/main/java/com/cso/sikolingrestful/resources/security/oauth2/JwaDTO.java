package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
import java.io.Serializable;
import java.util.Objects;


public class JwaDTO implements Serializable {
    private String id;
    private String nama;
    private String keterangan;
    private JwaTypeDTO jwa_type;

    public JwaDTO() {
    }
    
    public JwaDTO(Jwa t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.keterangan = t.getKeterangan();
            this.jwa_type = t.getJwa_type() != null ?
                    new JwaTypeDTO(t.getJwa_type()) : null;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public JwaTypeDTO getJwa_type() {
        return jwa_type;
    }

    public void setJwa_type(JwaTypeDTO jwa_type) {
        this.jwa_type = jwa_type;
    }
    
    public Jwa toJwa() {
        return new Jwa(
            this.id, 
            this.nama, 
            this.keterangan, 
            this.jwa_type != null ? this.jwa_type.toJwaType() : null
        );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 311 * hash + Objects.hashCode(this.id);
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
        final JwaDTO other = (JwaDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "{Key{ id:"
                .concat(this.id)
                .concat(", nama:")
                .concat(this.nama)
                .concat("}");
    }
    
}
