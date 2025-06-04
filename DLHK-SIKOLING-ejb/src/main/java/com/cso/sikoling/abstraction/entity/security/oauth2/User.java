package com.cso.sikoling.abstraction.entity.security.oauth2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class User implements Serializable {
    
    private final String id;
    private final String user_name;
    private final String password;
    private final Date tanggal_registrasi;
    private final String hashing_password_type_id;

    public User(String id, String user_name, String password, Date tanggal_registrasi, 
                                                String hashing_password_type_id) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
        this.tanggal_registrasi = tanggal_registrasi;
        this.hashing_password_type_id = hashing_password_type_id;
    }

    public String getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public String getHashing_password_type_id() {
        return hashing_password_type_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", user_name=" + user_name + '}';
    }
    
}
