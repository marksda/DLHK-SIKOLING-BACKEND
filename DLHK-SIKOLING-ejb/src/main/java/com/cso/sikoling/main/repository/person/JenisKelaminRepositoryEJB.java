package com.cso.sikoling.main.repository.person;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class JenisKelaminRepositoryEJB implements Repository<JenisKelamin, QueryParamFilters, Filter> {
    
    @Inject
    private JenisKelaminRepositoryJPA jenisKelaminRepositoryJPA;

    @Override
    public JenisKelamin updateId(String idLama, JenisKelamin t) throws SQLException {
        return jenisKelaminRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public JenisKelamin save(JenisKelamin t) throws SQLException {
        return jenisKelaminRepositoryJPA.save(t);
    }

    @Override
    public JenisKelamin update(JenisKelamin t) throws SQLException {
        return jenisKelaminRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return jenisKelaminRepositoryJPA.delete(id);
    }

    @Override
    public List<JenisKelamin> getDaftarData(QueryParamFilters q) {
        return jenisKelaminRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return jenisKelaminRepositoryJPA.getJumlahData(f);
    }
    
}
