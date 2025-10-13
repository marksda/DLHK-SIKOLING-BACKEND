package com.cso.sikolingrestful.resources.person;

import java.util.Objects;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikolingrestful.resources.alamat.AlamatDTO;
import com.cso.sikolingrestful.resources.alamat.KontakDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonDTO {
    
    private String id;
    private String nama;
    private JenisKelaminDTO jenis_kelamin;
    private AlamatDTO alamat;
    private String scan_ktp;
    private KontakDTO kontak;
    private Boolean status_verified;
    private String tanggal_registrasi;

    public PersonDTO() {
    }
    
    public PersonDTO(Person t) {
        if(t != null) {
            this.id = t.getId();
            this.nama = t.getNama();
            this.jenis_kelamin = t.getJenisKelamin() != null ? new JenisKelaminDTO(t.getJenisKelamin()) : null;
            this.alamat = t.getAlamat() != null ? new AlamatDTO(t.getAlamat()) : null;
            this.scan_ktp = t.getScanKTP();
            this.kontak = t.getKontak() != null ? new KontakDTO(t.getKontak()) : null;
            this.status_verified = t.getStatusVerified();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date tmpTglReg = t.getTanggal_registrasi();
            this.tanggal_registrasi = tmpTglReg != null ? df.format(tmpTglReg) : null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public JenisKelaminDTO getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(JenisKelaminDTO jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public AlamatDTO getAlamat() {
        return alamat;
    }

    public void setAlamat(AlamatDTO alamat) {
        this.alamat = alamat;
    }

    public String getScan_ktp() {
        return scan_ktp;
    }

    public void setScan_ktp(String scan_ktp) {
        this.scan_ktp = scan_ktp;
    }

    public KontakDTO getKontak() {
        return kontak;
    }

    public void setKontak(KontakDTO kontak) {
        this.kontak = kontak;
    }

    public Boolean getStatus_verified() {
        return status_verified;
    }

    public void setStatus_verified(Boolean status_verified) {
        this.status_verified = status_verified;
    }

    public String getTanggal_registrasi() {
        return tanggal_registrasi;
    }

    public void setTanggal_registrasi(String tanggal_registrasi) {
        this.tanggal_registrasi = tanggal_registrasi;
    }
    
    public Person toPerson() {
        if( this.id == null || this.nama == null || this.alamat == null || this.kontak == null ) {
            throw new IllegalArgumentException("format data json person tidak sesuai");
        }
        else {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = this.tanggal_registrasi != null ? df.parse(this.tanggal_registrasi) : null;
                return new Person(
                    this.id, 
                    this.nama, 
                    this.jenis_kelamin != null ? this.jenis_kelamin.toJenisKelamin() : null, 
                    this.alamat != null ? this.alamat.toAlamat() : null, 
                    scan_ktp, 
                    this.kontak != null ? this.kontak.toKontak() : null, 
                    this.status_verified,
                    date
                );
            } catch (ParseException ex) {
                return new Person(
                    this.id, 
                    this.nama, 
                    this.jenis_kelamin != null ? this.jenis_kelamin.toJenisKelamin() : null, 
                    this.alamat != null ? this.alamat.toAlamat() : null, 
                    scan_ktp, 
                    this.kontak != null ? this.kontak.toKontak() : null, 
                    this.status_verified,
                    null
                );
            }
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 751;
        hash = 71 * hash + Objects.hashCode(id );
        hash = 71 * hash + Objects.hashCode(nama);
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

        final PersonDTO other = (PersonDTO) obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return !(this.nama == null ? other.nama != null : !this.nama.equals(other.nama));
    }

    @Override
    public String toString() {
        if(id == null) {
            return null;
        }			

        return "personDTO{" + "id=" + this.id + ", nama=" + this.nama + "}";	    
    }

}
