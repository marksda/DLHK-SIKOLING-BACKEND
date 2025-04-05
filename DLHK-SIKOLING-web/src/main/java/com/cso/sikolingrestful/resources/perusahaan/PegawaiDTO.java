package com.cso.sikolingrestful.resources.perusahaan;

import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikolingrestful.resources.person.PersonDTO;
import java.util.Objects;


public class PegawaiDTO {

    private String id;
    private PerusahaanDTO perusahaan;
    private PersonDTO person;
    private JabatanDTO jabatan;

    public PegawaiDTO() {
    }
    
    public PegawaiDTO(Pegawai t) {
        if(t != null) {
            this.id = t.getId();
            this.perusahaan = t.getPerusahaan() != null ?
                    new PerusahaanDTO(t.getPerusahaan()) : null;
            this.person = t.getPerson() != null ?
                    new PersonDTO(t.getPerson()) : null;
            this.jabatan = t.getJabatan() != null ?
                    new JabatanDTO(t.getJabatan()) : null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PerusahaanDTO getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(PerusahaanDTO perusahaan) {
        this.perusahaan = perusahaan;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public JabatanDTO getJabatan() {
        return jabatan;
    }

    public void setJabatan(JabatanDTO jabatan) {
        this.jabatan = jabatan;
    }
    
    public Pegawai toPegawai() {
        if( this.id == null) {
            throw new IllegalArgumentException("format data json pegawai tidak sesuai");
        }
        else {
            return new Pegawai(
                    this.id, 
                    this.perusahaan != null ? this.perusahaan.toPerusahaan() : null, 
                    this.person != null ? this.person.toPerson() : null, 
                    this.jabatan != null ? this.jabatan.toJabatan() : null
            );
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final PegawaiDTO other = (PegawaiDTO) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return "PegawaiDTO{"
                .concat("id=")
                .concat(this.getId())
                .concat("}");
    }
    
}
