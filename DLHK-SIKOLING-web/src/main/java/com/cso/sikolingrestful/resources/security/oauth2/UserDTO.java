package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class UserDTO implements Serializable {
    
    private String id;
    private String user_name;
    private String password;
    private String tanggal_registrasi;
    private String hashing_password_type_id;
	
    public UserDTO() {		
    }

    public UserDTO(User t) {
        if(t != null) {
            this.id = t.getId();
            this.user_name = t.getUser_name();
            this.password = t.getPassword();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date tmpTglReg = t.getTanggal_registrasi();
            this.tanggal_registrasi = tmpTglReg != null ? df.format(tmpTglReg) : null;
            this.hashing_password_type_id = t.getHashing_password_type_id();
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

    public String getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(String tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public String getHashing_password_type_id() {
        return hashing_password_type_id;
    }

    public void setHashing_password_type_id(String hashing_password_type_id) {
        this.hashing_password_type_id = hashing_password_type_id;
    }    
    
    public User toUser() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = this.tanggal_registrasi != null ? df.parse(this.tanggal_registrasi) : null;
            return new User(
                this.id, 
                this.user_name, 
                this.password, 
                date, 
                this.hashing_password_type_id
            );
        }
        catch (ParseException ex) {
            return new User(
                this.id, 
                this.user_name, 
                this.password, 
                null, 
                this.hashing_password_type_id
            );
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
