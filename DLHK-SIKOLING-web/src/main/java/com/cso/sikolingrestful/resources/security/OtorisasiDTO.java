package com.cso.sikolingrestful.resources.security;

import java.io.Serializable;
import java.util.Date;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikolingrestful.resources.person.PersonDTO;
import java.util.Objects;


public class OtorisasiDTO implements Serializable {
    
    private String id;
    private String id_lama;
    private Boolean is_verified;
    private String user_name;
    private Date tanggal_registrasi;
    private HakAksesDTO hak_akses;
    private PersonDTO person;

    public OtorisasiDTO() {
    }
    
    public OtorisasiDTO(Otorisasi t) {
        if(t != null) {
            this.id = t.getId();
            this.id_lama = t.getId_lama();
            this.is_verified = t.getIs_verified();
            this.user_name = t.getUser_name();
            this.tanggal_registrasi = t.getTanggal_registrasi();
            this.hak_akses = t.getHak_akses() != null ?
                    new HakAksesDTO(t.getHak_akses()) : null;
            this.person = t.getPerson() != null ?
                    new PersonDTO(t.getPerson()) : null;
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

    public HakAksesDTO getHak_akses() {
        return hak_akses;
    }

    public void setHak_akses(HakAksesDTO hak_akses) {
        this.hak_akses = hak_akses;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }
    
    public Otorisasi toOtorisasi() {
        return new Otorisasi(
            this.id, 
            this.id_lama, 
            this.is_verified, 
            this.user_name, 
            this.tanggal_registrasi, 
            this.hak_akses != null ? this.hak_akses.toHakAkses() : null, 
            this.person != null ? this.person.toPerson() : null
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
        final OtorisasiDTO other = (OtorisasiDTO) obj;
        return Objects.equals(this.id, other.id);
    }

}
