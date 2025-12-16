package com.cso.sikoling.abstraction.entity.permohonan;

import java.io.Serializable;
import java.util.Objects;

public class StatusFlowPermohonan implements Serializable {
    
    private final String id;
    private final String keterangan;

    public StatusFlowPermohonan(String id, String keterangan) {
        this.id = id;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    @Override
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

        final StatusFlowPermohonan other = (StatusFlowPermohonan) obj;

        if (!this.id.equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "StatusFlowPermohonan {"
                .concat("id=")
                .concat(this.id)
                .concat(", keterangan=")
                .concat(this.keterangan)
                .concat("}");
    }	

}
