package com.cso.sikoling.abstraction.entity.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Autorisasi implements Serializable {

    private final String id;
    private final String id_user;
    private final Boolean status_internal;
    private final Boolean is_verified;
    private final String user_name;
    private final Date tanggal_registrasi;
    private final String id_hak_akses;
    private final String id_person;

    public Autorisasi(String id, String id_user, Boolean status_internal, Boolean is_verified, String user_name, Date tanggal_registrasi, String id_hak_akses, String id_person) {
        this.id = id;
        this.id_user = id_user;
        this.status_internal = status_internal;
        this.is_verified = is_verified;
        this.user_name = user_name;
        this.tanggal_registrasi = tanggal_registrasi;
        this.id_hak_akses = id_hak_akses;
        this.id_person = id_person;
    }

    public String getId() {
        return id;
    }

    public String getId_user() {
        return id_user;
    }

    public Boolean getStatus_internal() {
        return status_internal;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public String getUser_name() {
        return user_name;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public String getId_hak_akses() {
        return id_hak_akses;
    }

    public String getId_person() {
        return id_person;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Autorisasi other = (Autorisasi) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "Autorisasi {"
                .concat("id=")
                .concat(this.id)
                .concat(", user_name=")
                .concat(this.user_name)
                .concat("}");
    }	
    
    
}
