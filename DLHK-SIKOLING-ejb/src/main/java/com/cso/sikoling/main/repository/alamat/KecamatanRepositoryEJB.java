package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Kecamatan;
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
public class KecamatanRepositoryEJB implements Repository<Kecamatan, QueryParamFilters, Filter> {
    
    @Inject
    private KecamatanRepositoryJPA kecamatanRepositoryJPA;

    @Override
    public Kecamatan save(Kecamatan t) throws SQLException {
        return kecamatanRepositoryJPA.save(t);
    }

    @Override
    public Kecamatan update(Kecamatan t) throws SQLException {
        return kecamatanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kecamatanRepositoryJPA.delete(id);
    }

    @Override
    public Kecamatan updateId(String idLama, Kecamatan t) throws SQLException {
        return kecamatanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public List<Kecamatan> getDaftarData(QueryParamFilters q) {
        return kecamatanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kecamatanRepositoryJPA.getJumlahData(f);
    }

}
