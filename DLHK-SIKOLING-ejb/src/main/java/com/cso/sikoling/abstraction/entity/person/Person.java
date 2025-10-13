package com.cso.sikoling.abstraction.entity.person;

import com.cso.sikoling.abstraction.entity.alamat.Alamat;
import com.cso.sikoling.abstraction.entity.alamat.Kontak;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Person implements Serializable {
    
    private final String id;
    private final String nama;
    private final JenisKelamin jenisKelamin;
    private final Alamat alamat;
    private final String scanKTP;
    private final Kontak kontak;
    private final Boolean statusVerified;
    private final Date tanggal_registrasi;

    public Person(String id, String nama, JenisKelamin jenisKelamin, 
            Alamat alamat, String scanKTP, Kontak kontak, 
            Boolean statusVerified, Date tanggal_registrasi) {
        this.id = id;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.scanKTP = scanKTP;
        this.kontak = kontak;
        this.statusVerified = statusVerified;
        this.tanggal_registrasi = tanggal_registrasi;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public String getScanKTP() {
        return scanKTP;
    }

    public Kontak getKontak() {
        return kontak;
    }

    public Boolean getStatusVerified() {
        return statusVerified;
    }

    public JenisKelamin getJenisKelamin() {
        return jenisKelamin;
    }

    public Date getTanggal_registrasi() {
        return tanggal_registrasi;
    }
    
    @Override
    public int hashCode() {
        
        int hash = 83;
        hash = 103 * hash + Objects.hashCode(this.id);
        
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

        final Person other = (Person) obj;

        return this.id.equals(other.id);
        
    }
	
    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", nama=" + nama + ", alamat=" + alamat.toString() + "}";
    }
                                       
}
