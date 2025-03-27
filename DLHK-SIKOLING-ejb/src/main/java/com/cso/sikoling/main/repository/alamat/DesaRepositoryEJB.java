package com.cso.sikoling.main.repository.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.alamat.Desa;
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
public class DesaRepositoryEJB implements Repository<Desa, QueryParamFilters, Filter> {
    
    @Inject
    private DesaRepositoryJPA desaRepositoryJPA;

    @Override
    public Desa save(Desa t) throws SQLException {
        return desaRepositoryJPA.save(t);
    }

    @Override
    public Desa update(Desa t) throws SQLException {
        return desaRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return desaRepositoryJPA.delete(id);
    }

    @Override
    public Desa updateId(String idLama, Desa t) throws SQLException {
        return desaRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public List<Desa> getDaftarData(QueryParamFilters q) {
        return desaRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return desaRepositoryJPA.getJumlahData(f);
    }

}
