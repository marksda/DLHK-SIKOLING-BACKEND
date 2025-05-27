package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;



public class UserDTO implements Serializable {
    
    private String id;
    private String user_name;
    private String password;
    private Date tanggal_registrasi;
	
    public UserDTO() {		
    }

    public UserDTO(User t) {
        if(t != null) {
            this.id = t.getId();
            this.user_name = t.getUser_name();
            this.password = t.getPassword();
            this.tanggal_registrasi = t.getTanggal_registrasi();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(Date tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }
    
    public User toUser() {
        if(this.id == null) {
            throw new IllegalArgumentException("format data json propinsi tidak sesuai");
        }
        else {
            return new User(this.id, this.user_name, this.password, this.tanggal_registrasi);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.id, other.id);
    }	

}
