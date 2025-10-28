package com.cso.sikoling.abstraction.entity.security;

import com.cso.sikoling.abstraction.entity.person.Person;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Otorisasi implements Serializable {

    private final String id;
    private final String id_lama;
    private final Boolean is_verified;
    private final String user_name;
    private final Date tanggal_registrasi;
    private final HakAkses hak_akses;
    private final Person person;

    public Otorisasi(
            String id, String id_lama, Boolean is_verified, 
            String user_name, Date tanggal_registrasi,  HakAkses hak_akses, 
            Person person) {
        this.id = id;
        this.id_lama = id_lama;
        this.is_verified = is_verified;
        this.user_name = user_name;
        this.tanggal_registrasi = tanggal_registrasi;
        this.hak_akses = hak_akses;
        this.person = person;
    }

    public String getId() {
        return id;
    }

    public String getId_lama() {
        return id_lama;
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

    public HakAkses getHak_akses() {
        return hak_akses;
    }

    public Person getPerson() {
        return person;
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
        final Otorisasi other = (Otorisasi) obj;
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
