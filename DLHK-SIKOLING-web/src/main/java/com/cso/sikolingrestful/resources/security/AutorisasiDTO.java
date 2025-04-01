package com.cso.sikolingrestful.resources.security;

import java.io.Serializable;
import java.util.Date;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import java.util.Objects;


public class AutorisasiDTO implements Serializable {
    
    private String id;
    private String id_lama;
    private Boolean status_internal;
    private Boolean is_verified;
    private String user_name;
    private Date tanggal_registrasi;
    private String id_hak_akses;
    private String id_person;

    public AutorisasiDTO() {
    }
    
    public AutorisasiDTO(Autorisasi t) {
        if(t != null) {
            this.id = t.getId();
            this.id_lama = t.getId_lama();
            this.status_internal = t.getStatus_internal();
            this.is_verified = t.getIs_verified();
            this.user_name = t.getUser_name();
            this.tanggal_registrasi = t.getTanggal_registrasi();
            this.id_hak_akses = t.getId_hak_akses();
            this.id_person = t.getId_person();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_lama() {
        return id_lama;
    }

    public void setId_lama(String id_lama) {
        this.id_lama = id_lama;
    }

    public Boolean getStatus_internal() {
        return status_internal;
    }

    public void setStatus_internal(Boolean status_internal) {
        this.status_internal = status_internal;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(Date tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public String getId_hak_akses() {
        return id_hak_akses;
    }

    public void setId_hak_akses(String id_hak_akses) {
        this.id_hak_akses = id_hak_akses;
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }
    
    public Autorisasi toAutorisasi() {
        return new Autorisasi(
                this.id, 
                this.id_lama, 
                this.status_internal, 
                this.is_verified, 
                this.user_name, 
                this.tanggal_registrasi, 
                this.id_hak_akses, 
                this.id_person
            );
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final AutorisasiDTO other = (AutorisasiDTO) obj;
        return Objects.equals(this.id, other.id);
    }

}
