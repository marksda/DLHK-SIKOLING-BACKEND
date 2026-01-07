package com.cso.sikoling.abstraction.entity.perusahaan;

import com.cso.sikoling.abstraction.entity.person.Person;
import java.io.Serializable;
import java.util.Objects;

public class Pegawai implements Serializable {

    private final String id;
    private final Perusahaan perusahaan;
    private final Person person;
    private final Jabatan jabatan;
    private final Boolean statusAktif;

    public Pegawai(String id, Perusahaan perusahaan, Person person, Jabatan jabatan, Boolean statusAktif) {
        this.id = id;
        this.perusahaan = perusahaan;
        this.person = person;
        this.jabatan = jabatan;
        this.statusAktif = statusAktif;
    }

    public Person getPerson() {
        return person;
    }

    public String getId() {
        return id;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public Jabatan getJabatan() {
        return jabatan;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    @Override
    public int hashCode() {
        int hash = 137;
        hash = 121 * hash + Objects.hashCode(this.id);

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

        final Pegawai other = (Pegawai) obj;

        return this.id.equalsIgnoreCase(other.getId());
    }

    @Override
    public String toString() {
        return "Pegawai{"
                .concat("id=")
                .concat(this.getId())
                .concat("}");
    }

}
