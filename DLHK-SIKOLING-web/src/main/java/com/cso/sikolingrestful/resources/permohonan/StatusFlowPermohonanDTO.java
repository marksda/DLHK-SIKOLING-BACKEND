package com.cso.sikolingrestful.resources.permohonan;

import com.cso.sikoling.abstraction.entity.permohonan.StatusFlowPermohonan;
import java.io.Serializable;
import java.util.Objects;


public class StatusFlowPermohonanDTO implements Serializable {
    
    private String id;
    private String keterangan;

    public StatusFlowPermohonanDTO() {
    }
    
    public StatusFlowPermohonanDTO(StatusFlowPermohonan t) {
        if(t != null) {
            this.id = t.getId();
            this.keterangan = t.getKeterangan();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public StatusFlowPermohonan toStatusFlowPermohonan() {
        return new StatusFlowPermohonan(this.id, this.keterangan);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final StatusFlowPermohonanDTO other = (StatusFlowPermohonanDTO) obj;
        
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "StatusFlowPermohonanDTO{id="
                .concat(this.id)
                .concat(", keterangan=")
                .concat(this.keterangan)
                .concat("}");
    }

}
